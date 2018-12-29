package com.pdog18.transition.constraintlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionManager
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.pdog18.util.setOnToggleListener
import kotlinx.android.synthetic.main.activity_hencoder_raw.*

class HenCoderWithConstraintLayout : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hencoder_raw)
        title = this::class.java.canonicalName

        h.setOnToggleListener {
            TransitionManager.beginDelayedTransition(h.parent as ViewGroup)
            ConstraintSet().apply {
                this.clone(this@HenCoderWithConstraintLayout, if(it)R.layout.activity_hencoder_result else R.layout.activity_hencoder_raw)
            }.applyTo(h.parent as ConstraintLayout)
        }
    }
}
