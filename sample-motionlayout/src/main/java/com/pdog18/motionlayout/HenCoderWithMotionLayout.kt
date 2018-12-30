package com.pdog18.motionlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pdog18.util.setOnToggleListener
import kotlinx.android.synthetic.main.activity_hen_coder_with_motion_layout.*

class HenCoderWithMotionLayout : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hen_coder_with_motion_layout)
        c.setOnToggleListener { toggle ->
            if (toggle) {
                motion_layout_with_hencoder.setDebugMode(2)
            } else {
                motion_layout_with_hencoder.setDebugMode(1)
            }
        }
    }
}
