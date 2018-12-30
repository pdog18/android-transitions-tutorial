package com.pdog18.transition.sample_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import com.pdog18.util.setOnToggleListener
import kotlinx.android.synthetic.main.activity_begin_delayed.*

class BeginDelayedSampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_begin_delayed)
        title1.setOnToggleListener { visible ->
            TransitionManager.beginDelayedTransition(linear_layout)
            content1.visibility = if (visible) View.GONE else View.VISIBLE
        }


        title2.setOnToggleListener { visible ->
            TransitionManager.beginDelayedTransition(linear_layout)
            content2.visibility = if (visible) View.GONE else View.VISIBLE
        }
    }
}
