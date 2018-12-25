package com.pdog18.transition.rtfsc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.transition.ChangeBounds
import androidx.transition.Scene
import androidx.transition.TransitionManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var scene1: Scene
    private lateinit var scene2: Scene
    private var toggle = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scene1 = Scene.getSceneForLayout(scene_root, R.layout.scene1, this)
        scene2 = Scene.getSceneForLayout(scene_root, R.layout.scene2, this)
    }

    fun onFabClick(view: View) {
        if (toggle) {
            TransitionManager.go(scene1, ChangeBounds())
        } else {
            TransitionManager.go(scene2, ChangeBounds())
        }
        toggle = !toggle
    }

}
