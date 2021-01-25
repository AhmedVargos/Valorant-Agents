package com.ahmedvargos.uicomponents.utils

import android.view.View
import android.widget.ImageView
import com.ahmedvargos.uicomponents.R

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


fun ImageView.loadImage(url: String?) {
    if (url?.isNotEmpty() == true) {
        GlideApp.with(context)
            .load(url)
            .error(R.drawable.ic_broken_image_24)
            .into(this)
    } else
        GlideApp.with(context)
            .load(R.drawable.ic_broken_image_24)
            .into(this)
}