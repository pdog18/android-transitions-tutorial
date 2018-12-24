package com.pdog18.motionlayout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

    }

    fun openActivity(view: View) {
        val klass = Class.forName("com.pdog18.motionlayout.${view.contentDescription}")
        startActivity(Intent(this, klass))
    }
}
