package com.lok.dev.commonutil

import android.view.View

fun View.visible(visible: Boolean = true): Boolean {
    visibility = if (visible) View.VISIBLE else View.GONE
    return visible
}