package com.pdog18.custom

import android.animation.Animator
import android.view.View
import android.view.ViewGroup
import androidx.transition.TransitionValues
import androidx.transition.Visibility
import android.animation.ObjectAnimator
import android.animation.AnimatorSet

class Scale : Visibility() {

    private fun createAnimation(
        view: View,
        startScale: Float,
        endScale: Float
    ): Animator {
        return AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(view, View.SCALE_X, startScale, endScale),
                ObjectAnimator.ofFloat(view, View.SCALE_Y, startScale, endScale)
            )
        }
    }

    override fun onAppear(
        sceneRoot: ViewGroup?,
        view: View,
        startValues: TransitionValues,
        endValues: TransitionValues
    ) = createAnimation(view, 0f, 1f)

    override fun onDisappear(
        sceneRoot: ViewGroup?,
        view: View,
        startValues: TransitionValues,
        endValues: TransitionValues
    ) = createAnimation(view, 1f, 0f)
}