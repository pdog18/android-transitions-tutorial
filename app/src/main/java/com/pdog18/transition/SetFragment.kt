package com.pdog18.transition

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.transition.ChangeBounds
import androidx.transition.Slide
import com.pdog18.BaseFragment
import com.pdog18.Layout
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.pdog.dimension.dp
import kotlinx.android.synthetic.main.exclude.*


@Layout(R.layout.exclude)
class SetFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // magic
        button.setOnClickListener(object : View.OnClickListener {
            var visible: Boolean = false
            override fun onClick(v: View) {
                visible = !visible

                val slideTransition = Slide(Gravity.END)

                slideTransition.addTarget(text)
                val changeBoundsTransition = ChangeBounds()
                changeBoundsTransition.addTarget(view_toggle)

                val transitionSet = TransitionSet()
                transitionSet.addTransition(slideTransition)
                    .addTransition(changeBoundsTransition)

                TransitionManager.beginDelayedTransition(transitions_exclude_container, transitionSet)
                text.visibility = if (visible) View.VISIBLE else View.GONE
                text_exclude.visibility = if (visible) View.VISIBLE else View.GONE

                val layoutParams = view_toggle.layoutParams

                view_toggle.layoutParams = if (visible) {
                    layoutParams.apply {
                        height = 0.dp
                        width = 0.dp
                    }
                } else {
                    layoutParams.apply {
                        height = 100.dp
                        width = 100.dp
                    }
                }
            }
        })
    }
}