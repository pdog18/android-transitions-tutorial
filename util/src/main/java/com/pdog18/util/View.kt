package com.pdog18.util

import android.view.View

fun View.setOnToggleListener(action: (Boolean) -> Unit) {
    this.setOnClickListener(object : Toggle() {
        override fun onToggle(toggle: Boolean, view: View) {
            action(toggle)
        }
    })
}