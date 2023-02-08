package com.lok.dev.commonutil

import android.content.Context
import android.util.DisplayMetrics


fun Number.px(context: Context): Int {
    return this.toFloat().toPx(context)
}

fun Float.toPx(context: Context): Int {
    val densityDpi = context.resources.displayMetrics.densityDpi
    return (this * (densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
}