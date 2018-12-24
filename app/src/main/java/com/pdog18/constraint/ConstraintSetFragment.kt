package com.pdog18.constraint

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.TransitionManager
import com.pdog18.helper.BaseFragment
import com.pdog18.helper.Layout
import com.pdog18.transition.R
import kotlinx.android.synthetic.main.constranit_set_raw.*


@Layout(R.layout.constranit_set_raw)
class ConstraintSetFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        text_view.text = "很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长的文字!!"
        // magic
        parent.setOnClickListener(object : View.OnClickListener {
            var changed = false
            override fun onClick(v: View?) {
                changed = !changed

                val layoutId = if (changed)
                    R.layout.constranit_set_detail
                else
                    R.layout.constranit_set_raw

                if (changed) {
                    text_view.maxLines = 10
                }else{
                    text_view.maxLines = 1
                }

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