package com.lok.dev.commonutil

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import android.view.View
import androidx.lifecycle.lifecycleScope

fun Fragment.addFragment(
    @IdRes containerId: Int,
    fragment: Fragment?,
    fragmentManager: FragmentManager = childFragmentManager,
    addBackStack: Boolean = false,
    backStackName: String? = null
) {
    requireNotNull(fragment)

    val transaction = fragmentManager.beginTransaction()
    transaction.add(containerId, fragment).apply {
        if(addBackStack) addToBackStack(backStackName)
    }
    transaction.commitAllowingStateLoss()
}

fun Fragment.replaceFragment(
    @IdRes containerId: Int,
    fragment: Fragment?,
    fragmentManager: FragmentManager = childFragmentManager,
    addBackStack : Boolean = false
) {
    requireNotNull(fragment)

    val transaction = fragmentManager.beginTransaction()
    transaction.replace(containerId, fragment).apply {
        if (addBackStack) addToBackStack(null)
    }
    transaction.commitAllowingStateLoss()
}

fun Fragment.throttleFirstClick(view: View, period: Long = 1500, action: (View) -> Unit) {
    view.throttleFirst(period).collect(viewLifecycleOwner.lifecycleScope, action)
}