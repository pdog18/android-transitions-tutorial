package com.pdog18.go

import android.os.Bundle
import android.view.View
import androidx.transition.AutoTransition
import androidx.transition.ChangeBounds
import androidx.transition.Scene
import com.pdog18.BaseFragment
import com.pdog18.Layout
import androidx.transition.TransitionManager
import com.pdog18.transition.R
import kotlinx.android.synthetic.main.go_auto_parent.*
import kotlinx.android.synthetic.main.go_auto_scene1.*


@Layout(R.layout.go_auto_parent)
class GoAutoFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // magic
        button.setOnClickListener(object : View.OnClickListener {
            var visible: Boolean = false
            override fun onClick(v: View) {
                visible = !visible
                val scene1 = Scene(parent, container)
                val scene2 = Scene.getSceneForLayout(parent, R.layout.go_auto_scene2, context!!)

                if (!visible)
                    TransitionManager.go(scene1)
                else
                    TransitionManager.go(scene2)
            }
        })

        button_delay.setOnClickListener(object : View.OnClickListener {
            var visible: Boolean = false
            override fun onClick(v: View) {
                visible = !visible

                val changeBounds = ChangeBounds()

                TransitionManager.beginDelayedTransition(parent, changeBounds)
                // todo change layout params
            }
        })
    }
}