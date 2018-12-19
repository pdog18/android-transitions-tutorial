package com.pdog18.constraint

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.TransitionManager
import com.pdog18.helper.BaseFragment
import com.pdog18.helper.Layout
import com.pdog18.transition.R
import kotlinx.android.synthetic.main.summary.*


@Layout(R.layout.summary)
class ConstraintSetFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // magic
        parent.setOnClickListener(object : View.OnClickListener {
            var changed = false
            override fun onClick(v: View?) {
                changed = !changed

                val layoutId = if (changed)
                    R.layout.detail
                else
                    R.layout.summary

                change(layoutId)
            }
        })
    }


    private fun change(layoutId: Int) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(context, layoutId)
        TransitionManager.beginDelayedTransition(parent)
        constraintSet.applyTo(parent)
    }
}