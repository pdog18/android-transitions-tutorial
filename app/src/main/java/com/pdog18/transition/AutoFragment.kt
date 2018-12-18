package com.pdog18.transition

import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.transition.AutoTransition
import com.pdog18.BaseFragment
import com.pdog18.Layout
import androidx.transition.TransitionManager
import kotlinx.android.synthetic.main.auto.*


@Layout(R.layout.auto)
class AutoFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // magic
        button.setOnClickListener(object : View.OnClickListener {
            var visible: Boolean = false
            override fun onClick(v: View) {
                val transition = AutoTransition().apply {
                    interpolator = OvershootInterpolator()
                }
                TransitionManager.beginDelayedTransition(transitions_auto_container,transition)
                visible = !visible
                text.visibility = if (visible) View.VISIBLE else View.GONE
            }
        })


        // pale
        button_pale.setOnClickListener(object : View.OnClickListener {
            var visible: Boolean = false
            override fun onClick(v: View) {
                visible = !visible
                text_pale.visibility = if (visible) View.VISIBLE else View.GONE
            }
        })
    }
}