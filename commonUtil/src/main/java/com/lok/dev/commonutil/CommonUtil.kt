package com.lok.dev.commonutil

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.DisplayMetrics
import androidx.annotation.ColorRes
import androidx.annotation.FontRes
import androidx.core.content.ContextCompat


fun Number.px(context: Context): Int {
    return this.toFloat().toPx(context)
}

fun Float.toPx(context: Context): Int {
    val densityDpi = context.resources.displayMetrics.densityDpi
    return (this * (densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
}

fun Context.getChangeTextStyle(
    str: String,
    changeStr: String,
    @ColorRes colorRes: Int,
    @FontRes fontRes: Int = 0
): SpannableStringBuilder = SpannableStringBuilder(str).also { ssb ->
    val strIdx = str.indexOf(changeStr)
    val start = strIdx.takeIf { it > -1 } ?: 0
    val end = strIdx.takeIf { it > -1 }.let { start + changeStr.length }
    val flag = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE

    ssb.setSpan(ForegroundColorSpan(ContextCompat.getColor(this, colorRes)), start, end, flag)
    fontRes.takeIf { it > 0 }?.run { ssb.setSpan(StyleSpan(Typeface.BOLD), start, end, flag) }
}