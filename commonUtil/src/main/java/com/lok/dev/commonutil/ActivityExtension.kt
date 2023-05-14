package com.lok.dev.commonutil

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowInsets
import android.view.WindowManager
import android.view.WindowMetrics
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.addFragment(
    @IdRes containerId: Int,
    fragment: Fragment?,
    addBackStack: Boolean = false
) {
    requireNotNull(fragment)

    val transaction = supportFragmentManager.beginTransaction()
    transaction.add(containerId, fragment).apply {
        if (addBackStack) addToBackStack(null)
    }
    transaction.commitAllowingStateLoss()
}

fun FragmentActivity.replaceFragment(
    @IdRes containerId: Int,
    fragment: Fragment?,
    addBackStack: Boolean = false
) {
    requireNotNull(fragment)

    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(containerId, fragment).apply {
        if (addBackStack) addToBackStack(null)
    }
    transaction.commitAllowingStateLoss()
}

fun FragmentActivity.removeFragment(fragment: Fragment?) {
    if (fragment == null) return

    val transaction = supportFragmentManager.beginTransaction()
    transaction.remove(fragment)
    transaction.commitAllowingStateLoss()
}

fun FragmentActivity.getWindowWidth(): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics: WindowMetrics = windowManager.currentWindowMetrics
        val insets = windowMetrics.windowInsets
            .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
        windowMetrics.bounds.width() - insets.left - insets.right
    } else {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        displayMetrics.widthPixels
    }
}

fun FragmentActivity.getDeviceHeight(): Int {
    val mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = mWindowManager.defaultDisplay
    val size = Point()
    display.getSize(size)
    return size.y
}