package com.pdog18.transition.sample_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.transition.TransitionManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // magic
        button.setOnClickListener(object : View.OnClickListener {
            private var toggle = false
            override fun onClick(v: View?) {
                toggle = !toggle
                TransitionManager.beginDelayedTransition(scene_root)
                text.visibility = if (toggle) View.GONE else View.VISIBLE
            }
        })
    }
}
