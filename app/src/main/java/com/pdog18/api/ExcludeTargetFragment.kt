package com.pdog18.api

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.transition.Slide
import com.pdog18.BaseFragment
import com.pdog18.Layout
import androidx.transition.TransitionManager
import com.pdog18.transition.R
import kotlinx.android.synthetic.main.exclude.*


@Layout(R.layout.exclude)
class ExcludeTargetFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // magic
        button.setOnClickListener(object : View.OnClickListener {
            var visible: Boolean = false
            override fun onClick(v: View) {
                val transition = Slide(Gravity.END)

                transition.excludeTarget(text_exclude, true)

                TransitionManager.beginDelayedTransition(transitions_exclude_container, transition)
                visible = !visible
                text.visibility = if (visible) View.VISIBLE else View.GONE
                text_exclude.visibility = if (visible) View.VISIBLE else View.GONE
            }
        })
    }
}