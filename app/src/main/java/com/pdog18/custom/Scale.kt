package com.pdog18.custom

import android.animation.Animator
import android.view.View
import android.view.ViewGroup
import androidx.transition.TransitionValues
import androidx.transition.Visibility
import android.animation.ObjectAnimator
import androidx.transition.Transition
import androidx.transition.TransitionListenerAdapter
import android.animation.AnimatorSet


class Scale : Visibility() {

    private val PROPNAME_SCALE_X = "scale:scaleX"
    private val PROPNAME_SCALE_Y = "scale:scaleY"

    override fun captureStartValues(transitionValues: TransitionValues) {
        super.captureStartValues(transitionValues)
        transitionValues.values.put(PROPNAME_SCALE_X, transitionValues.view.getScaleX());
        transitionValues.values.put(PROPNAME_SCALE_Y, transitionValues.view.getScaleY());
    }

    private fun createAnimation(
        view: View,
        startScale: Float,
        endScale: Float, values: TransitionValues?
    ): Animator {
        val initialScaleX = view.scaleX
        val initialScaleY = view.scaleY
        var startScaleX = initialScaleX * startScale
        val endScaleX = initialScaleX * endScale
        var startScaleY = initialScaleY * startScale
        val endScaleY = initialScaleY * endScale

        if (values != null) {
            val savedScaleX = values.values[PROPNAME_SCALE_X] as Float
            val savedScaleY = values.values[PROPNAME_SCALE_Y] as Float
            // if saved value is not equal initial value it means that previous
            // transition was interrupted and in the onTransitionEnd
            // we've applied endScale. we should apply proper value to
            // continue animation from the interrupted state
            if (savedScaleX != initialScaleX) {
                startScaleX = savedScaleX
            }
            if (savedScaleY != initialScaleY) {
                startScaleY = savedScaleY
            }
        }

        view.scaleX = startScaleX
        view.scaleY = startScaleY

        val animator = AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(view, View.SCALE_X, startScaleX, endScaleX),
                ObjectAnimator.ofFloat(view, View.SCALE_Y, startScaleY, endScaleY)
            )
        }
        addListener(object : TransitionListenerAdapter() {
            override fun onTransitionEnd(transition: Transition) {
                view.scaleX = initialScaleX
                view.scaleY = initialScaleY
                transition.removeListener(this)
            }
        })
        return animator
    }


    override fun onAppear(
        sceneRoot: ViewGroup?,
        view: View,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ) = createAnimation(view, 0f, 1f, startValues)

    override fun onDisappear(
        sceneRoot: ViewGroup?,
        view: View,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ) = createAnimation(view, 1f, 0f, startValues)
}