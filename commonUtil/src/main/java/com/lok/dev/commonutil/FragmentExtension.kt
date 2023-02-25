package com.lok.dev.commonutil

import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.lok.dev.commonmodel.state.AnimationState

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
        if (addBackStack) addToBackStack(backStackName)
    }
    transaction.commitAllowingStateLoss()
}

fun Fragment.replaceFragment(
    @IdRes containerId: Int,
    fragment: Fragment?,
    fragmentManager: FragmentManager = childFragmentManager,
    addBackStack: Boolean = false
) {
    requireNotNull(fragment)

    val transaction = fragmentManager.beginTransaction()
    transaction.replace(containerId, fragment).apply {
        if (addBackStack) addToBackStack(null)
    }
    transaction.commitAllowingStateLoss()
}

fun Fragment.removeFragment(
    fragment: Fragment?,
    fragmentManager: FragmentManager = childFragmentManager,
    animation: AnimationState = AnimationState.None,
    popBackStack: Boolean = true
) {
    if (fragment == null) return

    val transaction = fragmentManager.beginTransaction()
    animFragment(transaction, animation)
    transaction.remove(fragment)
    if (popBackStack) fragmentManager.popBackStack()
    transaction.commitAllowingStateLoss()
}

fun Fragment.throttleFirstClick(view: View, period: Long = 1500, action: (View) -> Unit) {
    view.throttleFirst(period).collect(viewLifecycleOwner.lifecycleScope, action)
}