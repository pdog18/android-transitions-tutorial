package com.pdog18.motionlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    private var toggle = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        view.setOnClickListener {
            toggle = !toggle
            if (toggle) {
                ml.transitionToStart()
            } else {
                ml.transitionToEnd()
            }
        }
    }
}
