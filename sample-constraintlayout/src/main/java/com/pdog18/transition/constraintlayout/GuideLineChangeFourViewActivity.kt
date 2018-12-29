package com.pdog18.transition.constraintlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.transition.TransitionManager
import kotlinx.android.synthetic.main.activity_main2.*

class GuideLineChangeFourViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
    }

    fun onChangeGuide(view: View) {
        TransitionManager.beginDelayedTransition(constraint_parent)

        val (v, h) = GuidePair(view.tag as String)
        guide_v.setGuidelinePercent(v)
        guide_h.setGuidelinePercent(h)
    }

    class GuidePair(tag: String) {
        private var v = 0f
        private var h = 0f

        init {
            val pair = tag.split(",")
            v = pair[0].trim().toFloat()
            h = pair[1].trim().toFloat()
        }

        operator fun component1() = v

        operator fun component2() = h
    }
}
