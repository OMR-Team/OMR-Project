package com.lok.dev.commonutil

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope

fun LifecycleOwner.throttleFirstClick(view: View, period: Long = 1500, action: (View) -> Unit) {
    view.throttleFirst(period).collect(lifecycleScope, action)
}