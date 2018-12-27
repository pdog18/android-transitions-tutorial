package com.pdog18.transition.sample_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.pdog18.util.setOnToggleListener
import kotlinx.android.synthetic.main.activity_target.*

class TargetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_target)
        target_parent.setOnToggleListener { toggle ->
            val slide = Slide()
                .addTarget(left_top)
                .addTarget(R.id.left_center)
                .addTarget("left_bottom")
                .addTarget(ImageView::class.java)

            TransitionManager.beginDelayedTransition(target_parent, slide)
            left_top.visibility = if (toggle) View.GONE else View.VISIBLE
            left_bottom.visibility = if (toggle) View.GONE else View.VISIBLE

            left_center.visibility = if (toggle) View.GONE else View.VISIBLE
            right_center.visibility = if (toggle) View.GONE else View.VISIBLE


            right_top.visibility = if (toggle) View.GONE else View.VISIBLE
            right_bottom.visibility = if (toggle) View.GONE else View.VISIBLE
        }
    }
}
