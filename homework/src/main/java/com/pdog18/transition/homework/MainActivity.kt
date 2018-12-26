package com.pdog18.transition.homework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.transition.Scene
import androidx.transition.TransitionManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        motion_layout.setOnClickListener(object : View.OnClickListener {
            private var toggle = true
            override fun onClick(v: View?) {
                Log.e("tag", "toogle${toggle}")
                toggle = !toggle
                if (toggle) {
                    TransitionManager.beginDelayedTransition(motion_layout)
                    guide_v.setGuidelinePercent(0.3f)
                    guide_h.setGuidelinePercent(0.7f)
                } else
                    motion_layout.transitionToEnd()
            }
        })
    }
}
