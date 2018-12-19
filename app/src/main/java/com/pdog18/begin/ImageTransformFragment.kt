package com.pdog18.begin

import android.os.Bundle
import android.view.View
import com.pdog18.helper.BaseFragment
import com.pdog18.helper.Layout
import kotlinx.android.synthetic.main.image_transform.*
import android.view.ViewGroup
import android.widget.ImageView
import androidx.transition.*
import com.pdog18.transition.R


@Layout(R.layout.image_transform)
class ImageTransformFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        image.setOnClickListener(object : View.OnClickListener {
            var expanded: Boolean = true
            override fun onClick(v: View) {
                TransitionManager.beginDelayedTransition(
                    transitions_image_container,
                    TransitionSet()
                        .addTransition(ChangeBounds())
                        .addTransition(ChangeImageTransform())
                )

                val params = image.layoutParams
                params.height = if (expanded)
                    ViewGroup.LayoutParams.MATCH_PARENT
                else
                    ViewGroup.LayoutParams.WRAP_CONTENT
                image.layoutParams = params

                expanded = !expanded

                image.scaleType = if (expanded)
                    ImageView.ScaleType.CENTER_CROP
                else
                    ImageView.ScaleType.FIT_CENTER
            }
        })
    }
}