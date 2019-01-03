package com.pdog18.transition.sample_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.TransitionManager
import kotlinx.android.synthetic.main.scene_card_end_bottom.*

class FourCardSelectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scene_card_start_top)
    }

    fun onChangeGuide(view: View) {
        TransitionManager.beginDelayedTransition(scene_root)

        val who = view.contentDescription

        val constraintSet = ConstraintSet().apply {
            val layoutId = resources.getIdentifier("scene_card_$who", "layout", packageName)
            clone(this@FourCardSelectActivity, layoutId)
        }

        val constraintLayoutId = resources.getIdentifier("scene_root_constraint_$who", "id", packageName)

        val innerConstraintLayout = view.findViewById<ConstraintLayout>(constraintLayoutId)
        val innerCs = ConstraintSet().apply {
            clone(innerConstraintLayout)
        }


        val progressBarId = resources.getIdentifier("progress_bar_$who", "id", packageName)
        val tvPriceId = resources.getIdentifier("tv_${who}_price", "id", packageName)
        innerCs.setVisibility(progressBarId, View.GONE)
        innerCs.setVisibility(tvPriceId, View.VISIBLE)
        innerCs.applyTo(innerConstraintLayout)
        constraintSet.applyTo(scene_root)
    }
}
