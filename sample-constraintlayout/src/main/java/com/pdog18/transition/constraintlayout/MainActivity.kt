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

        view_2.text = "HenCoder : 给高级 Android 工程师的进阶手册。"

        val raw = ConstraintSet().apply {
            this.clone(this@MainActivity, R.layout.constranit_set_raw)
        }

        val detail = ConstraintSet().apply {
            this.clone(this@MainActivity, R.layout.constranit_set_detail)
        }


        constraint_parent.setOnToggleListener { toggle ->
            val constraintSet = if (toggle) detail else raw
            TransitionManager.beginDelayedTransition(constraint_parent)
            constraintSet.applyTo(constraint_parent)
        }
    }
}
