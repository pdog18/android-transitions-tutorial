# android-transitions-tutorial


## 前言
> 每当我们实现一个精彩的动画，增加了应用的活力的时候，内心总会为之兴奋。
>
> 而过渡动画的存在无疑是帮助我们将这项工作推向完美的利器，而 Android 过渡动画框架则能在提高我们开发效率时尽可能的保证了代码的优雅。
>
> 同时因为前端方向，不管是FE，还是iOS，或者其他桌面开发都存在着过渡动画。
>
> 如果可以对过渡动画有比较清晰的了解，那么显然可以在 Android 以外的场景应用到，有点 “一次学习，到处使用” 的感觉。

在刚开始准备的时候，我有点不知所措、无从下手的感觉，经过一些思考，逐渐有些许思路。

写在下面帮助我整理和回顾

### 目的

做一件事，显然需要有一个目的，我设计这个「课程」的目的是什么呢？

* 从技术角度来说，那就是了解 Android 过渡动画框架，会使用对应的 API 完成需求

* 从学习角度来说，脱离 Android 过渡动画框架，对前端开发中的过渡动画得到一些思考

* 从设计角度来说，对提高国内应用的活力、表现力、交互感作出一些努力



### 手段

1. 介绍**设计层面**的 **过渡动画**（Andriod 、CSS3 、KeyNote 等过渡效果）

   对比一个场景在使用过渡动画前后的效果。

2. 然后通过 Android 的 API 实现一些常见的过渡动画 

   1. `TransitionManager.beginDelayedTransition()`
   2. `TransitionManager.go()`
      * 介绍`Scene` 的概念
      * 介绍`Transition` 的概念
        * 不同`transition` 的分类
      * 介绍`go()` 和 `beginDelayedTransitio()`的相同之处和不同之处
   3. `ConstraintSet` 关键帧动画
      * 介绍`Scene` 和`ConstranitSet`的的相同之处和不同之处
      * `MotionLayout`
      * `Keyframe Animations` 关键帧，通过关键帧调整动画曲线
   4. 在能力范围内的源码解析

      * 理解为什么过渡动画会生效
   5. 共享元素介绍及使用
      * `Activity` 和`Fragment Transition` 

3. 能力之内的源码解读
   * `TransitionManager`的源码解读
   * 尝试不通过`TransitionManager`实现一个简单的过渡帮助工具

4. 课程回顾
   * 对设计层面的过渡动画在编码层面的一个整体抽象总结
   * 对（2）、（3）中的内容的简单总结


