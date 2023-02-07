package com.lok.dev.commonutil

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope

fun Fragment.throttleFirstClick(view: View, period: Long = 1500, action: (View) -> Unit) {
    view.throttleFirst(period).collect(viewLifecycleOwner.lifecycleScope, action)
}