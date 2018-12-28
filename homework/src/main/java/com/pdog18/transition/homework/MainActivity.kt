package com.pdog18.transition.homework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        motion_layout.setOnClickListener(object : View.OnClickListener {
            private var toggle = true
            override fun onClick(v: View?) {
                toggle = !toggle
                if (toggle) {
                    motion_layout.transitionToStart()
                } else
                    motion_layout.transitionToEnd()
            }
        })
    }
}
