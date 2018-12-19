package com.pdog18.begin

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.transition.Slide
import com.pdog18.helper.BaseFragment
import com.pdog18.helper.Layout
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
                visible = !visible

                val slideTransition = Slide(Gravity.END)

                slideTransition.excludeTarget(text_exclude, true)

                TransitionManager.beginDelayedTransition(transitions_exclude_container, slideTransition)
                text.visibility = if (visible) View.VISIBLE else View.GONE
                text_exclude.visibility = if (visible) View.VISIBLE else View.GONE
            }
        })
    }
}