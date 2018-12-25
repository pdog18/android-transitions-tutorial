[TOC]

# Transition 源码分析

### TransitionValues

```java
public class TransitionValues {

    public final Map<String, Object> values = new HashMap<>();

    public View view;

    final ArrayList<Transition> mTargetedTransitions = new ArrayList<>();
    // ...
}

```



先看这个类的结构

* view 
* values ：Transition 的实现类定义的属性名，Object 是 Transition 实现类保存的数据。
* targetedTransition：保存 View 需要使用的各种 Transition。



### TrasitionValuesMaps

```java
class TransitionValuesMaps {

    final ArrayMap<View, TransitionValues> mViewValues = new ArrayMap<>();

    final SparseArray<View> mIdValues = new SparseArray<>();

    final LongSparseArray<View> mItemIdValues = new LongSparseArray<>();

    final ArrayMap<String, View> mNameValues = new ArrayMap<>();

}
```

四个 Map。

* viewValues：保存 View 和 Transitions 对
* idValues：保存 view。getId() 和 View 对
* itemIdValues：针对 ListView的，保存 ListView 的 item id 和 View 对
* nameValues：保存 View.getTransitionName() 和 View 对



### Scene.getSceneForLayout()

```java
    @NonNull
    public static Scene getSceneForLayout(@NonNull ViewGroup sceneRoot, @LayoutRes int layoutId,
            @NonNull Context context) {
        @SuppressWarnings("unchecked")
        SparseArray<Scene> scenes =
                (SparseArray<Scene>) sceneRoot.getTag(R.id.transition_scene_layoutid_cache);
        if (scenes == null) {
            scenes = new SparseArray<>();
            sceneRoot.setTag(R.id.transition_scene_layoutid_cache, scenes);
        }
        Scene scene = scenes.get(layoutId);
        if (scene != null) {
            return scene;
        } else {
            scene = new Scene(sceneRoot, layoutId, context);
            scenes.put(layoutId, scene);
            return scene;
        }
    }

```

如果不考虑缓存优化，简单的代码如下：

```java
return new Scene(sceneRoot, layoutId, context);
```



### 执行过渡动画

`TransitionManager.go()`

```java
public static void go(@NonNull Scene scene, @Nullable Transition transition) {
    changeScene(scene, transition);
}


private static void changeScene(Scene scene, Transition transition) {
    final ViewGroup sceneRoot = scene.getSceneRoot();

    if (!sPendingTransitions.contains(sceneRoot)) {
        if (transition == null) {
            scene.enter();
        } else {
            sPendingTransitions.add(sceneRoot);

            Transition transitionClone = transition.clone();
            transitionClone.setSceneRoot(sceneRoot);

            Scene oldScene = Scene.getCurrentScene(sceneRoot);
            if (oldScene != null && oldScene.isCreatedFromLayoutResource()) {
                transitionClone.setCanRemoveViews(true);
            }

            sceneChangeSetup(sceneRoot, transitionClone);

            scene.enter();

            sceneChangeRunTransition(sceneRoot, transitionClone);
        }
    }
}
```

#### 调用 `TransitionManager.go()` 完成动画之前，经历了三步。



##### 第一步——保存视图信息 `sceneChangeSetup()`

`TransitionManager#sceneChangeSetup`

```java
   private static void sceneChangeSetup(ViewGroup sceneRoot, Transition transition) {
        // Capture current values
        ArrayList<Transition> runningTransitions = getRunningTransitions().get(sceneRoot);

        if (runningTransitions != null && runningTransitions.size() > 0) {
            for (Transition runningTransition : runningTransitions) {
                runningTransition.pause(sceneRoot);
            }
        }

        if (transition != null) {
            transition.captureValues(sceneRoot, true);
        }

        // Notify previous scene that it is being exited
        Scene previousScene = Scene.getCurrentScene(sceneRoot);
        if (previousScene != null) {
            previousScene.exit();
        }
    }
```

关注 最重要的代码 `transition.captureValues(sceneRoot, true);`

使用 `transition.captureValues(sceneRoot, true)`; 捕获sceneRoot 的数据保存到 transition 中，第二个参数 true 表示转换发生前， false 表示转换发生后。这个方法会针对这个参数分别作出不同的行为。



###### Transition 的 `captureValues()` 方法

```java
    void captureValues(ViewGroup sceneRoot, boolean start) {
        clearValues(start);
        if ((mTargetIds.size() > 0 || mTargets.size() > 0)
                && (mTargetNames == null || mTargetNames.isEmpty())
                && (mTargetTypes == null || mTargetTypes.isEmpty())) {
            for (int i = 0; i < mTargetIds.size(); ++i) {
                int id = mTargetIds.get(i);
                View view = sceneRoot.findViewById(id);
                if (view != null) {
                    TransitionValues values = new TransitionValues();
                    values.view = view;
                    if (start) {
                        captureStartValues(values);
                    } else {
                        captureEndValues(values);
                    }
                    values.mTargetedTransitions.add(this);
                    capturePropagationValues(values);
                    if (start) {
                        addViewValues(mStartValues, view, values);
                    } else {
                        addViewValues(mEndValues, view, values);
                    }
                }
            }
            for (int i = 0; i < mTargets.size(); ++i) {
                View view = mTargets.get(i);
                TransitionValues values = new TransitionValues();
                values.view = view;
                if (start) {
                    captureStartValues(values);
                } else {
                    captureEndValues(values);
                }
                values.mTargetedTransitions.add(this);
                capturePropagationValues(values);
                if (start) {
                    addViewValues(mStartValues, view, values);
                } else {
                    addViewValues(mEndValues, view, values);
                }
            }
        } else {
            captureHierarchy(sceneRoot, start);
        }
        if (!start && mNameOverrides != null) {
            int numOverrides = mNameOverrides.size();
            ArrayList<View> overriddenViews = new ArrayList<>(numOverrides);
            for (int i = 0; i < numOverrides; i++) {
                String fromName = mNameOverrides.keyAt(i);
                overriddenViews.add(mStartValues.mNameValues.remove(fromName));
            }
            for (int i = 0; i < numOverrides; i++) {
                View view = overriddenViews.get(i);
                if (view != null) {
                    String toName = mNameOverrides.valueAt(i);
                    mStartValues.mNameValues.put(toName, view);
                }
            }
        }
    }
```

因为没有为 Transition 设置 target，所以直接看`captureHierarchy(sceneRoot, start)`，这个方法用来保存 sceneRoot 信息，以及遍历 sceneRoot 的 children，并且递归调用 `captureHierarchy()` 来保存所有 children 的信息。

> 上面的代码也可以看出，如果一个 Transition 如果没有指定任意 target，那么 sceneRoot 下面的所有 view 都会保存信息（除非排除），同时还会一直递归下去。而有 target 的情况只会对**直接**子View 有影响。



###### Transition 的 `captureHierarchy()`

```java
    private void captureHierarchy(View view, boolean start) {
        if (view == null) {
            return;
        }
        int id = view.getId();
        if (mTargetIdExcludes != null && mTargetIdExcludes.contains(id)) {
            return;
        }
        if (mTargetExcludes != null && mTargetExcludes.contains(view)) {
            return;
        }
        if (mTargetTypeExcludes != null) {
            int numTypes = mTargetTypeExcludes.size();
            for (int i = 0; i < numTypes; ++i) {
                if (mTargetTypeExcludes.get(i).isInstance(view)) {
                    return;
                }
            }
        }
        if (view.getParent() instanceof ViewGroup) {
            TransitionValues values = new TransitionValues();
            values.view = view;
            if (start) {
                captureStartValues(values);
            } else {
                captureEndValues(values);
            }
            values.mTargetedTransitions.add(this);
            capturePropagationValues(values);
            if (start) {
                addViewValues(mStartValues, view, values);
            } else {
                addViewValues(mEndValues, view, values);
            }
        }
        if (view instanceof ViewGroup) {
            // Don't traverse child hierarchy if there are any child-excludes on this view
            if (mTargetIdChildExcludes != null && mTargetIdChildExcludes.contains(id)) {
                return;
            }
            if (mTargetChildExcludes != null && mTargetChildExcludes.contains(view)) {
                return;
            }
            if (mTargetTypeChildExcludes != null) {
                int numTypes = mTargetTypeChildExcludes.size();
                for (int i = 0; i < numTypes; ++i) {
                    if (mTargetTypeChildExcludes.get(i).isInstance(view)) {
                        return;
                    }
                }
            }
            ViewGroup parent = (ViewGroup) view;
            for (int i = 0; i < parent.getChildCount(); ++i) {
                captureHierarchy(parent.getChildAt(i), start);
            }
        }
    }
```

保存数据的关键位置 ` captureStartValues()/captureEndValues()`，这两个方法分别用来保存转换前/后 布局的信息，它们在 Transition 中抽象方法，每个实现类都要重写这个方法，写自己的实现。我们根据`ChangeBounds#captureStartValues()`来分析。



###### `ChangeBounds#captureStartValues()`

```java
@Override
public void captureStartValues(@NonNull TransitionValues transitionValues) {
    captureValues(transitionValues);
}
```

```java
private void captureValues(TransitionValues values) {
    View view = values.view;

    if (ViewCompat.isLaidOut(view) || view.getWidth() != 0 || view.getHeight() != 0) {
        values.values.put(PROPNAME_BOUNDS, new Rect(view.getLeft(), view.getTop(),
                                                    view.getRight(), view.getBottom()));
        values.values.put(PROPNAME_PARENT, values.view.getParent());
        if (mReparent) {
            values.view.getLocationInWindow(mTempLocation);
            values.values.put(PROPNAME_WINDOW_X, mTempLocation[0]);
            values.values.put(PROPNAME_WINDOW_Y, mTempLocation[1]);
        }
        if (mResizeClip) {
            values.values.put(PROPNAME_CLIP, ViewCompat.getClipBounds(view));
        }
    }
}
```

关注这两行代码，可以看出 TransitionValues.values 保存的是什么数据

```java
values.values.put(PROPNAME_BOUNDS, new Rect(view.getLeft(), view.getTop(),
                                            view.getRight(), view.getBottom()));
values.values.put(PROPNAME_PARENT, values.view.getParent());
```

然后回到 `captureHierarchy()` 的 `addViewValues()` 方法，这个方法是把刚刚创建的局部变量`values` 保存到`mStartValues/mEndValues` 中。



到此为止，我们已经将过渡之前的布局中的每个 view (包括sceneRoot 本身)的信息保存到了 `mStartValues(TransitionValuesMaps)`中。



##### 第二步——布局的切换 `scene.enter()`

```java
// scene.enter();
public void enter() {
    // Apply layout change, if any
    if (mLayoutId > 0 || mLayout != null) {
        // empty out parent container before adding to it
        getSceneRoot().removeAllViews();

        if (mLayoutId > 0) {
            LayoutInflater.from(mContext).inflate(mLayoutId, mSceneRoot);
        } else {
            mSceneRoot.addView(mLayout);
        }
    }

    // Notify next scene that it is entering. Subclasses may override to configure scene.
    if (mEnterAction != null) {
        mEnterAction.run();
    }

    setCurrentScene(mSceneRoot, this);
}
```

在 `enter()`中，将所有 children 移出，然后添加新的 layout。

> 可以通过 Debug 直观的查看 ChildCount

然后将 scene 保存到 View 中。



##### 第三步，生成并执行动画 `sceneChangeRunTransition()`

这一步是创建 Animator，并运行。

```java
private static void sceneChangeRunTransition(final ViewGroup sceneRoot,
                                             final Transition transition) {
    if (transition != null && sceneRoot != null) {
        MultiListener listener = new MultiListener(transition, sceneRoot);
        sceneRoot.addOnAttachStateChangeListener(listener);
        sceneRoot.getViewTreeObserver().addOnPreDrawListener(listener);
    }
}
```

创建了一个 `MultiListener` 并且添加到了 `sceneRoot` 的 `addOnAttachStateChangeListener`  和`addOnPreDrawListener`。

```java
private static class MultiListener implements ViewTreeObserver.OnPreDrawListener,
View.OnAttachStateChangeListener {

    Transition mTransition;

    ViewGroup mSceneRoot;

    MultiListener(Transition transition, ViewGroup sceneRoot) {
        mTransition = transition;
        mSceneRoot = sceneRoot;
    }

    private void removeListeners() {
        mSceneRoot.getViewTreeObserver().removeOnPreDrawListener(this);
        mSceneRoot.removeOnAttachStateChangeListener(this);
    }

    
    @Override
    public boolean onPreDraw() {
        removeListeners();

        // Don't start the transition if it's no longer pending.
        if (!sPendingTransitions.remove(mSceneRoot)) {
            return true;
        }

        // Add to running list, handle end to remove it
        final ArrayMap<ViewGroup, ArrayList<Transition>> runningTransitions =
            getRunningTransitions();
        ArrayList<Transition> currentTransitions = runningTransitions.get(mSceneRoot);
        ArrayList<Transition> previousRunningTransitions = null;
        if (currentTransitions == null) {
            currentTransitions = new ArrayList<>();
            runningTransitions.put(mSceneRoot, currentTransitions);
        } else if (currentTransitions.size() > 0) {
            previousRunningTransitions = new ArrayList<>(currentTransitions);
        }
        currentTransitions.add(mTransition);
        mTransition.addListener(new TransitionListenerAdapter() {
            @Override
            public void onTransitionEnd(@NonNull Transition transition) {
                ArrayList<Transition> currentTransitions = runningTransitions.get(mSceneRoot);
                currentTransitions.remove(transition);
            }
        });
        mTransition.captureValues(mSceneRoot, false);
        if (previousRunningTransitions != null) {
            for (Transition runningTransition : previousRunningTransitions) {
                runningTransition.resume(mSceneRoot);
            }
        }
        mTransition.playTransition(mSceneRoot);

        return true;
    }
    
//..... about attach/detach
}
```

其中`OnAttachStateChangeListener` 处理一些资源释放等操作不关注。

那么就剩下一个 `OnPreDrawListener` 中的`onPreDraw()`回调了。

查看它的核心实现：

```java
// step1 保存 end scene 的信息
mTransition.captureValues(mSceneRoot, false);
if (previousRunningTransitions != null) {
    for (Transition runningTransition : previousRunningTransitions) {
        // 继续运行之前暂停的 transition
        runningTransition.resume(mSceneRoot);
    }
}
// step2 创建并运行动画
mTransition.playTransition(mSceneRoot);
```

在第二步中，`remove`，`addView` 的时候必然会导致试图的刷新，所以 `onPreDraw()` 肯定会被调用。

`mTransition.captureValues(mSceneRoot, false);`这里会再保存一次结束时的控件信息。

`mTransition.playTransition(mSceneRoot);`这里是重点，创建动画，并且执行动画。

```java
void playTransition(ViewGroup sceneRoot) {
    mStartValuesList = new ArrayList<>();
    mEndValuesList = new ArrayList<>();
    matchStartAndEnd(mStartValues, mEndValues);

    ArrayMap<Animator, AnimationInfo> runningAnimators = getRunningAnimators();
    int numOldAnims = runningAnimators.size();
    WindowIdImpl windowId = ViewUtils.getWindowId(sceneRoot);
    for (int i = numOldAnims - 1; i >= 0; i--) {
        Animator anim = runningAnimators.keyAt(i);
        if (anim != null) {
            AnimationInfo oldInfo = runningAnimators.get(anim);
            if (oldInfo != null && oldInfo.mView != null
                && windowId.equals(oldInfo.mWindowId)) {
                TransitionValues oldValues = oldInfo.mValues;
                View oldView = oldInfo.mView;
                TransitionValues startValues = getTransitionValues(oldView, true);
                TransitionValues endValues = getMatchedTransitionValues(oldView, true);
                boolean cancel = (startValues != null || endValues != null)
                    && oldInfo.mTransition.isTransitionRequired(oldValues, endValues);
                if (cancel) {
                    if (anim.isRunning() || anim.isStarted()) {
                        anim.cancel();
                    } else {
                        runningAnimators.remove(anim);
                    }
                }
            }
        }
    }

    createAnimators(sceneRoot, mStartValues, mEndValues, mStartValuesList, mEndValuesList);
    runAnimators();
}
```

先将`TransitionValuesMaps#mViewValues` 全部加入到 `unmatchedStart/unmatchedEnd`集合中，顾名思义没有匹配的。

然后通过`id` ，`transitionName`，`itemId(listview专有)` 等规则找到前后对应的控件并且记录它们的前后属性。找到后就从`unmatchedmap` 中删除。

最后如果剩下没有匹配的 view 那么就会在最后的`addUnmatched()` 方法中将它和`null`一一对应。

回到 `playTransition()` 中，创建动画`createAnimators()`然后`animator`会被播放。



#### 结束状态 View 已经不存在了怎么办？

> 题外话：结束状态没有的 View ，使用 `ViewOverlay` 来做动画。
>
> `Visibility#onDisappear()`中就是使用 `overLayView`来实现的。







### 总结

过渡动画执行原理分三步走

1. 保存起始的状态

2. 加载结束的状态

3. 对比起始和结束的状态，创建属性动画

   然后在 `onPreView()`中运行动画