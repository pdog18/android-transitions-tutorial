package com.pdog18.transition.constraintlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.TransitionManager
import com.pdog18.util.setOnToggleListener
import kotlinx.android.synthetic.main.constranit_set_raw.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.constranit_set_raw)

        constraint_parent.setOnToggleListener { toggle ->
            val layoutId = if (toggle) R.layout.constranit_set_detail else R.layout.constranit_set_raw
            change(layoutId)
        }
    }


    private fun change(layoutId: Int) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(this, layoutId)
        TransitionManager.beginDelayedTransition(constraint_parent)
        constraintSet.applyTo(constraint_parent)
    }
}
