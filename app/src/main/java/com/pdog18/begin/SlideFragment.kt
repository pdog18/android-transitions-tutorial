package com.pdog18.begin

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.transition.Slide
import com.pdog18.helper.BaseFragment
import com.pdog18.helper.Layout
import androidx.transition.TransitionManager
import com.pdog18.transition.R
import kotlinx.android.synthetic.main.slide.*


@Layout(R.layout.slide)
class SlideFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // magic
        button_slide.setOnClickListener(object : View.OnClickListener {
            var visible: Boolean = false
            override fun onClick(v: View) {
                val slide = Slide(Gravity.END)

                TransitionManager.beginDelayedTransition(transitions_slide_container, slide)
                visible = !visible
                text_slide.visibility = if (visible) View.VISIBLE else View.GONE
            }
        })


        // pale
        button_slide_pale.setOnClickListener(object : View.OnClickListener {
            var visible: Boolean = false
            override fun onClick(v: View) {
                visible = !visible
                text_slide_pale.visibility = if (visible) View.VISIBLE else View.GONE
            }
        })
    }
}