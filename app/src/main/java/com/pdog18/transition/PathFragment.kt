package com.pdog18.transition

import android.os.Bundle
import android.view.View
import com.pdog18.BaseFragment
import com.pdog18.Layout
import androidx.transition.TransitionManager
import kotlinx.android.synthetic.main.path.*
import android.view.Gravity
import android.widget.FrameLayout
import androidx.transition.ArcMotion
import androidx.transition.ChangeBounds


@Layout(R.layout.path)
class PathFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // magic
        button.setOnClickListener(object : View.OnClickListener {
            var isReturnAnimation: Boolean = true
            override fun onClick(v: View) {
                isReturnAnimation = !isReturnAnimation

                TransitionManager.beginDelayedTransition(
                    transitionsContainer,
                    ChangeBounds().apply {
                        setPathMotion(ArcMotion())
                        duration = 500
                    })

                val params = button.layoutParams as FrameLayout.LayoutParams
                params.gravity = if (isReturnAnimation)
                    Gravity.START or Gravity.TOP
                else
                    Gravity.BOTTOM or Gravity.END
                button.layoutParams = params
            }
        })

        pale.setOnClickListener(object : View.OnClickListener {
            var isReturnAnimation: Boolean = false
            override fun onClick(v: View?) {
                isReturnAnimation = !isReturnAnimation

                val params = pale.layoutParams as FrameLayout.LayoutParams
                params.gravity = if (isReturnAnimation)
                    Gravity.START or Gravity.BOTTOM
                else
                    Gravity.TOP or Gravity.END
                pale.layoutParams = params
            }

        })
    }
}