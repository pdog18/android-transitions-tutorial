[TOC]



# 介绍「过渡」



## HenCoder 官网

HenCoder 官网首页](https://hencoder.com/) 打开时的动画效果

* 提问：如果用代码实现这个移动加渐渐显示的效果，你会怎么做？
* 提示：根据结束位置，对不同的控件做位移动画和修改透明度动画。



## KeyNote 神奇过渡

打开对应文件夹展示神奇过渡效果 (后期可以尝试实现)



## CSS3 过渡

[CSS3 过渡](http://www.w3school.com.cn/css3/css3_transition.asp)





### 开场白

每当我们实现一个精彩的动画，增加了应用的活力的时候，内心总会为之兴奋。

而过渡动画的存在无疑是帮助我们将这项工作推向完美的利器，而 Android 过渡动画框架则能在提高我们开发效率时尽可能的保证了代码的优雅。

同时因为前端方向，不管是FE，还是iOS，或者其他桌面开发都存在着过渡动画。

如果可以对过渡动画有比较清晰的了解，那么显然可以在 Android 以外的场景应用到，有点 “一次学习，到处使用” 的感觉。





# Android TransitionManager

## TransitionManager.beginDelayedTransition()

代码展示

* 将一个控件显示的过渡动画

  通过点击一个 Button 展示一个 TextView Slide 进入 的动画过程

  首先建立一个没有动画的场景，介绍代码逻辑

  使用最简单的代码`TransitionManager.beginDelayedTransition(container,transition)`

* 将一个控件改变摆放位置的过渡动画

  1. 展示直线运动
  2. 显示曲线运动
  3. 对比没有过渡动画的情况

* 一个父容器中有部分控件不想使用动画，但是也被添加上了动画效果怎么办？

  使用 `Transition.excludeTarget(target,true)`方法来排除不想收到影响的控件

* 组合动画

  对一个控件同时做改变大小和滑入的动画，介绍`TransitionSet`



## TransitionManager.go()

### 介绍`Scene` 的概念

明白两个场景的概念，不要受原来的编码影响。

因为受到编码影响，会认为我们是在为开始的场景编写一个属性动画，然后属性动画完成后的结果正好是结束的状态。

或者另一种思维，动画启动时隐藏原来的 View ，然后创建一个新的View 做动画。

我们应该脱离代码来思考，它们本身就是两个静止的场景，我们首先有了这两个场景，然后要做的就是怎么把这两个场景联系起来。

> 此时可以回到 KeyNote 中切换不同的过渡效果

>  用什么联系 Scene？
>
> 就是 `Transition`



### 介绍`Transition` 的概念

- 不同`transition` 的分类，也就是对那些属性修改敏感
  * TextScale：support 包的内部实现动态修改字体大小
  * ChangeBounds ：View 尺寸改变
  * Visibility： 可见/不可见切换
  * ChangeImageTransform ：图片缩放模式改变
- 自定义一个`ScaleTransition`，因为系统的`ChangeBounds` 不会对可见性感兴趣
  1. 创建一个类继承 `androidx.transition.Visibility`，注意是 `transition`下的
  2. 重写`onAppear` 和 `onDisappear` 方法，返回一个`Animator` 对象
  3. 这个`Animator` 对象就是一个同时修改`View.SCALE_X`和 `View.SCALE_Y`的`AnimatorSet`属性动画





## TransitionManager 源码解读

查看 `Transition/rtfsc/README.md`





#### `beginDelayedTransition()` 和 `go()` 的区别

`go()` 在 `enter()` 中会进行 `removeAllViews()` 和 `addView`

`beginDelayedTransition()` 需要自己对布局修改，触发`MultiListener#onPreView()`

#### 





# ConstraintLayout

>  提问：谁听说过 ConstraintLayout ？使用过？



## ConstraintLayout 使用快速介绍。

约束：用皮筋举例，

* start,top,end,bottom,baseline 

* bais 偏向，百分比等
* circle

生成代码：拖动控件或者直接写（个人建议直接写，更好理解，效率高，可能会 ConstraintSet 理解有轻微帮助）



- 优点：扁平化，减少布局深度提高性能，不再深入

- 建议：不要强行去使用，根据界面模块(符合直觉)划分，提高开发效率

  [设计稿，建议使用线性布局，UI整体调整时也会更方便](https://dribbble.com/shots/5738155-Hair-salon-app/attachments)



## ConstraintSet 动画

介绍`Scene` 和`ConstranitSet`的的相同之处和不同之处。

* 控件是否都会被重新创建，查看源码得知总是会被`inflate`

* 控件是否会被重新添加，这关系到了`onAttachedToWindow`等生命周期方法的调用。

  * 提问：觉得哪个更符合直觉？说一下以往的动画通过欺骗手段（Drag 那节课）
  * 举例一个验证码倒计时控件，它对`onAttachedToWindow` 有处理时会有什么区别

  看`ConstranitSet.clone()`源码似乎可以优化（不需要每次都`inflate`出来），但是是否有必要需要讨论。








# MotionLayout

> 提问：谁听说过 MotionLayout





## 过渡和触摸事件的关系，没有太遥远

> **过渡动画** 其实可以更加宽泛一些，例如两个场景直接的切换。
> 好比ViewPager的滑动，正式因为滑动时会显示两个卡片之间滑动过程，所以让用户感觉更加舒服（想象一下用户在卡片上滑动，滑动时没有反馈，抬起时直接进行到下一页，时多么糟糕的体验啊！）。
>
> 通过上面这个例子，其实我们也可以认为，只要是场景直接的切换，我们就可以通过合适的过渡动画来丰满它，给它更多的生命力。





## 使用MotionLayout 实现例子

* 图片滤镜过渡
* 一个固定大小的容器内，四个不同大小的 View ，点击其中某个，放大被点击的 View ，同时缩小其他 View