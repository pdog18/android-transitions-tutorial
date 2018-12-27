package com.pdog18.go

import android.os.Bundle
import android.view.View
import androidx.transition.Scene
import com.pdog18.helper.BaseFragment
import com.pdog18.helper.Layout
import androidx.transition.TransitionManager
import com.pdog18.util.Toggle
import com.pdog18.transition.R
import kotlinx.android.synthetic.main.go_auto_parent.*
import kotlinx.android.synthetic.main.go_auto_scene1.*

@Layout(R.layout.go_auto_parent)
class GoAutoFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val scene1 = Scene(parent, container)
        val scene2 = Scene.getSceneForLayout(parent, R.layout.go_auto_scene2, context!!)

        // magic
        button.setOnClickListener(object : Toggle() {
            override fun onToggle(toggle: Boolean, view: View) {
                if (!toggle)
                    TransitionManager.go(scene1)
                else
                    TransitionManager.go(scene2)
            }
        })
    }
}