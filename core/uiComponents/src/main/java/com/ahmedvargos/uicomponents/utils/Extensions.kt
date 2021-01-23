package com.ahmedvargos.uicomponents.utils

import android.view.View
import android.widget.ImageView

/**
 * Extension method to provide show keyboard for View.
 */
fun View.gone() {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
}

/**
 * Extension method to provide show keyboard for View.
 */
fun View.visible() {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
}


fun ImageView.loadImage(url: String) {
    if (url.isNotEmpty()) {
        GlideApp.with(context)
            .load(url)
            .into(this)
    }
}