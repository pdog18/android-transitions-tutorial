package com.pdog18.motionlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pdog18.util.setOnToggleListener
import kotlinx.android.synthetic.main.activity_key_frame.*

class KeyFrameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_key_frame)
        view_key_frame.setOnToggleListener { toggle ->
            if (toggle) {
                motion_layout_key_frame.transitionToEnd()
            } else {
                motion_layout_key_frame.transitionToStart()
            }
        }
    }
}
