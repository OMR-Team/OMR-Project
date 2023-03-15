package com.lok.dev.commonutil

import android.content.Context
import android.util.DisplayMetrics

fun Number.toDp(context: Context): Float {
    val densityDpi = context.resources.displayMetrics.densityDpi
    return this.toFloat() / (densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}