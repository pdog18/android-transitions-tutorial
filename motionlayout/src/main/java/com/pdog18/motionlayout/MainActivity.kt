package com.pdog18.motionlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        motion_layout.apply {
            loadLayoutDescription(R.xml.scene_03)
            setShowPaths(true)
        }


        toggle.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                motion_layout.transitionToStart()
            } else {
                motion_layout.transitionToEnd()
            }
        }
    }
}
