package com.pdog18.begin

import android.os.Bundle
import android.view.View
import androidx.transition.AutoTransition
import com.pdog18.helper.BaseFragment
import com.pdog18.helper.Layout
import androidx.transition.TransitionManager
import com.pdog18.transition.R
import kotlinx.android.synthetic.main.auto.*


@Layout(R.layout.auto)
class AutoFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // magic
        button.setOnClickListener(object : View.OnClickListener {
            var visible: Boolean = false
            override fun onClick(v: View) {
                TransitionManager.beginDelayedTransition(transitions_auto_container,AutoTransition())
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