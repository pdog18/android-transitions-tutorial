package com.pdog18.transition.sample_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.pdog18.util.setOnToggleListener
import kotlinx.android.synthetic.main.activity_exclude.*

class ExcludeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exclude)
        exclude_parent.setOnToggleListener { toggle ->
            val slide = Slide()
                .excludeTarget(left_top, true)
                .excludeTarget(ImageView::class.java, true)

            TransitionManager.beginDelayedTransition(exclude_parent, slide)
            left_top.visibility = if (toggle) View.GONE else View.VISIBLE
            left_bottom.visibility = if (toggle) View.GONE else View.VISIBLE

            left_center.visibility = if (toggle) View.GONE else View.VISIBLE
            right_center.visibility = if (toggle) View.GONE else View.VISIBLE


            right_top.visibility = if (toggle) View.GONE else View.VISIBLE
            right_bottom.visibility = if (toggle) View.GONE else View.VISIBLE
        }
    }
}
