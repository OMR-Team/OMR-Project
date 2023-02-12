package com.lok.dev.commonutil

import android.content.Context
import android.util.TypedValue

fun Context.convertDpToPx(dp: Float) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, this.resources.displayMetrics)