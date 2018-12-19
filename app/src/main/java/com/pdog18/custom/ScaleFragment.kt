package com.pdog18.custom

import android.os.Bundle
import android.view.View
import com.pdog18.BaseFragment
import com.pdog18.Layout
import androidx.transition.TransitionManager
import com.pdog18.transition.R
import kotlinx.android.synthetic.main.scale.*


@Layout(R.layout.scale)
class ScaleFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // magic
        button_scale.setOnClickListener(object : View.OnClickListener {
            var visible: Boolean = false
            override fun onClick(v: View) {
                val scale = Scale()

                TransitionManager.beginDelayedTransition(transitions_scale_container, scale)
                visible = !visible
                text_scale.visibility = if (visible) View.VISIBLE else View.GONE
            }
        })


        // pale
        button_scale_pale.setOnClickListener(object : View.OnClickListener {
            var visible: Boolean = false
            override fun onClick(v: View) {
                visible = !visible
                text_scale_pale.visibility = if (visible) View.VISIBLE else View.GONE
            }
        })
    }
}