package com.pdog18.util

import android.view.View

abstract class Toggle : View.OnClickListener {
    private var toggle = false

    final override fun onClick(v: View) {
        toggle = !toggle
        onToggle(toggle, v)
    }

    abstract fun onToggle(toggle: Boolean, view: View)
}