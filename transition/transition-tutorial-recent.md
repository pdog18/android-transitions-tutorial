[TOC]



# Android Transition Framework 

> 首先定义一个需求——点击标题隐藏/展开内容，先是通过 Gone 来设置，然后这时候 UI提了需求需要添加动画。



## TransitionManager.beginDelayedTransition() 

### beginDelayedTransition() 展示

```kotlin
// toggle listener
button.setOnToggleListener { toggle ->
    TransitionManager.beginDelayedTransition(scene_root)
    text.visibility = if (toggle) View.GONE else View.VISIBLE
}
```



> 如果你要做这个动画，你要怎么做？

```kotlin
// 属性动画，缩放
button.setOnToggleListener { toggle ->
    val scaleFactor = if (toggle) 0f else 1f
    text.animate()
        .scaleX(scaleFactor)
        .scaleY(scaleFactor)
        .setUpdateListener {
            Log.e("Height -> ", text.height.toString())
        }
        .start()
}
```

属性动画？怎么处理平级控件的位置？



### addTarget() 展示

使用 4 种方式添加 target。

* id
* View 实例
* `android:transitionName`
* Class 类名



### excludeTarget()  展示

当然也有排除的 target

默认情况下是怎么样的 （全部参与动画）





### Transition 展示

> TODO : 需要一个展示大多数 Transition 的例子，介绍 Transition 

* Visibility 

  * Fade
  * Slide
  * Explode
* ChangeBounds
* ChangeImageTransform
* TextScale：support 包的内部实现动态修改字体大小
* 介绍 最有用的 ChangeBounds 和 Fade



## TransitionManager.go() 展示



### go() 展示



介绍 Scene 对象（场景释义）

> 这样可以有 scene 的概念



###  源码讲解 (结合 KeyNote)



#### 介绍 TransitionManager 对象

重点在它的两个方法上

* `go()`
* `beginDelayedTransition()`



#### 介绍 TransitionValues 对象

#### 介绍 TransitionValuesMaps 对象



#### 从 `go()` 开始讲解



#### 对比 go()  和 beginDelayedTransition() 的区别

* 一个需要将子 View 重新 remove 和 add ，一个不需要
* 一个编写不同的状态比较容易



#### 用 TextView 动态设置内容举例

前后布局因为动画框架，动态设置的内容消失了。

想一下是因为什么原因？

有没有更好的解决办法？—— 引出 ConstraintLayout。




## ConstraintLayout

### 约束布局介绍

约束：用弹簧举例

- start,top,end,bottom,baseline 
- bais 偏向，百分比等
- circle
- guideline

举例说明，怎么把一个控件相对另外一个控件的边做居中处理？



生成代码：拖动控件或者直接写（个人建议直接写，更好理解，效率高，可能会 ConstraintSet 理解有轻微帮助）



- 优点：扁平化，减少布局深度提高性能，不再深入
- 建议：不要强行去使用，根据界面模块(符合直觉)划分，提高开发效率
  - 如果你需要做动画，那么另说了



### ConstraintSet 动画

介绍`Scene` 和`ConstranitSet`的的相同之处和不同之处。

- 控件是否都会被重新创建，查看源码得知总是会被`inflate`
- 控件是否会被重新添加，这关系到了`onAttachedToWindow`等生命周期方法的调用。
  - 提问：觉得哪个更符合直觉？说一下以往的动画通过欺骗手段（Drag 那节课）
  - 举例一个动态设置的 TextView 内容消失

* 简单介绍 ConstraintSet 支持的一些方法

  * `centerVertically()` 
  * `setVisibility()`
  * 其他


### ConstraintSet 仍然有问题

ConstraintSet 解决了` go() `（状态消失）和` beginDelayed() `（代码管理状态复杂）的问题，但是仍然存在一些问题。

* 需要编写两个 xml，不直观，也不容易维护，能不能在一个文件里管理呢？
* xml 中有很多属性冗余，能不能精简？

所以引入用 MotionScene 文件管理的 MotionLayout



## MotionLayout

### 使用 MotionScene 编写过渡动画

**核心：让过渡过程变得可预期**

> todo: sample-motionlayout

1. 声明依赖

   ```groovy
   implementation 'androidx.constraintlayout:constraintlayout:2.0.0-alpha3'
   ```

2. 布局修改为 `MotionLayout`

3. 创建`MotionScene`文件，使用`app:layoutDescription` 引用



#### 第一种方案，引用布局

```xml
<Transition 
            motion:constraintSetStart="@+id/layout_start"
			motion:constraintSetEnd="@+id/layout_end">
</Transition>
```

#### 第二种方案，引用`ConstraintSet`

```xml
<Transition
        motion:constraintSetEnd="@+id/end"
        motion:constraintSetStart="@+id/start">
</Transition>


<ConstraintSet android:id="@+id/start">
    <Constraint>
    </Constraint>
</ConstraintSet>

<ConstraintSet android:id="@+id/end">
    <Constraint>
    </Constraint>
</ConstraintSet>
```



### 添加 关键帧

#### 介绍`framePosition`

这个需要配合坐标系统来讲解



#### 修改曲线拟合模型`motion:curveFit`

默认是样条，通过修改为线形来讲解样条的作用



#### 坐标系统`motion:keyPositionType`

* pathRelative（起始点和结束点确定 坐标系 X轴）

* deltaRelative (起始点和结束点确定(0,0:1,1)坐标系，所以他们相差值少的时候不好用)

* parentRelative（MotionLayout确定坐标系）

* motion:percentY (支持负值)

* motion:percentX (支持负值)

通过坐标图讲解区别，特别是介绍 `pathRelative`

```xml
<Transition         
        motion:constraintSetEnd="@+id/end"
        motion:constraintSetStart="@+id/start">
    <KeyFrameSet>
        <KeyPosition
                motion:framePosition="50"
                motion:curveFit="linear"
                motion:keyPositionType="parentRelative"
                motion:percentY="0.75"
                motion:target="@id/view_key_frame"/>
    </KeyFrameSet>
</Transition>
```



#### 帧属性 

通过关键帧属性对控件进行一些动画操作

* 旋转
* 缩放
* 透明度修改
* 高度(Z轴)调整

```xml
<Transition
        motion:constraintSetEnd="@+id/end"
        motion:constraintSetStart="@+id/start">
    <KeyFrameSet>
        <KeyAttribute android:scaleX="2"
                      motion:framePosition="50"
                      motion:target="@id/view_key_frame"
                      android:scaleY="2"
                      android:translationZ="16dp"
                      android:rotation="-45"/>
    </KeyFrameSet>
</Transition>
```



`android:visibility`, `android:alpha`, `android:elevation`, `android:rotation`, `android:rotationX`, `android:rotationY`, `android:scaleX`, `android:scaleY`, `android:translationX`, `android:translationY`, `android:translationZ`



### Arc Motion

如何设置曲线模型

* 默认的起始/结束之间是直线运动，通过起始位置的`pathMotionArc` 修改
* 通过关键帧来做曲线运动，怎么把关键帧的曲线修改成直线 
* 通过关键帧做翻转的曲线运动 `motion:pathMotionArc="flip"`









### setProgress

配合 ViewPager `setProgress()`来控制 MotionLayout 的过渡进度

> todo: ViewPager sample



### 过渡和触摸事件的关系，没有太遥远

用 MotionLayout 实现一个类似 ViewPager 的控件。

> todo: ViewPager 控件

然后通过这个控件讲解过渡和触摸的关联。

**过渡动画** 其实可以更加宽泛一些，例如两个场景直接的切换。
好比ViewPager的滑动，正式因为滑动时会显示两个卡片之间滑动过程，所以让用户感觉更加舒服（想象一下用户在卡片上滑动，滑动时没有反馈，抬起时直接进行到下一页，是多么糟糕的体验啊！）。

通过上面这个例子，其实我们也可以认为，只要是场景直接的切换，我们就可以通过合适的过渡动画来丰满它，给它更多的生命力。



# 限制

MotionLayout 将仅仅为它的直接子视图提供上述功能——这点正好和 TransitionManger 不一样，TransitionManager 不仅还可以作用于内嵌布局，还可以作用于 Activity 场景动画。



### 使用 MotionLayout 实现例子

* 图片滤镜过渡

* (已完成) 一个固定大小的容器内，四个子 View ，放大被点击的 View ，同时缩小其他 View
* 文字过渡