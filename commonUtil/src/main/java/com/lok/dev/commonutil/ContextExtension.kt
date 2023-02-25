package com.lok.dev.commonutil

import android.content.Context
import android.util.TypedValue
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.convertDpToPx(dp: Float) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, this.resources.displayMetrics)

fun Context.showToast(message: String?, duration: Int = Toast.LENGTH_SHORT): Unit =
    SingleToast.showToast(this, message, duration)

fun Context.showToast(@StringRes message: Int?, duration: Int = Toast.LENGTH_SHORT): Unit =
    SingleToast.showToast(this, message, duration)