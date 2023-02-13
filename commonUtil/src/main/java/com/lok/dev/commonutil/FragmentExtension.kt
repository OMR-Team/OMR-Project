package com.lok.dev.commonutil

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

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
        if(addBackStack) addToBackStack(null)
    }
    transaction.commitAllowingStateLoss()
}