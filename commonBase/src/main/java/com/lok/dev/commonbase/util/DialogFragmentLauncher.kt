package com.lok.dev.commonbase.util

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.lok.dev.commonbase.BaseConfirmDialog
import com.lok.dev.commonbase.BaseDialogFragment
import kotlinx.coroutines.Job

// DialogFragment 런처
fun <Binding : ViewDataBinding, Result> Fragment.launchDialogFragment(
    dialogFragment: BaseDialogFragment<Binding, Result>,
    fragmentManager: FragmentManager = childFragmentManager,
    cancelableOnTouchOutside: Boolean = true,
    fullScreen: Boolean = false,
    bottomSlideAnimation: Boolean = false,
    result: (Result?) -> Unit = {},
    cancel: () -> Unit = {}
): Job = showDialogFragment(
    dialogFragment = dialogFragment,
    fragmentManager = fragmentManager,
    cancelableOnTouchOutside = cancelableOnTouchOutside,
    fullScreen = fullScreen,
    bottomSlideAnimation = bottomSlideAnimation,
    result = result,
    cancel = cancel
)

fun <Binding : ViewDataBinding, Result> FragmentActivity.launchDialogFragment(
    dialogFragment: BaseDialogFragment<Binding, Result>,
    fragmentManager: FragmentManager = supportFragmentManager,
    cancelableOnTouchOutside: Boolean = true,
    fullScreen: Boolean = false,
    bottomSlideAnimation: Boolean = false,
    result: (Result?) -> Unit = {},
    cancel: () -> Unit = {}
): Job = showDialogFragment(
    dialogFragment = dialogFragment,
    fragmentManager = fragmentManager,
    cancelableOnTouchOutside = cancelableOnTouchOutside,
    fullScreen = fullScreen,
    bottomSlideAnimation = bottomSlideAnimation,
    result = result,
    cancel = cancel
)

private fun <VB : ViewDataBinding, Result> LifecycleOwner.showDialogFragment(
    dialogFragment: BaseDialogFragment<VB, Result>,
    fragmentManager: FragmentManager,
    tag: String? = null,
    cancelableOnTouchOutside: Boolean,
    fullScreen: Boolean = false,
    bottomSlideAnimation: Boolean = false,
    result: (Result?) -> Unit,
    cancel: () -> Unit
): Job = lifecycleScope.launchWhenStarted {
    dialogFragment.canceledOnTouchOutside = cancelableOnTouchOutside
    dialogFragment.fullScreen = fullScreen
    dialogFragment.bottomSlideAnimation = bottomSlideAnimation
    dialogFragment.result = result
    dialogFragment.cancel = cancel
    dialogFragment.show(fragmentManager, tag)
}

fun <Dialog : BaseConfirmDialog<*, Result>, Result> FragmentActivity.launchConfirmDialog(
    type: Class<Dialog>,
    args: Bundle? = null,
    tag: String? = null,
    fragmentManager: FragmentManager = supportFragmentManager,
    option: Dialog.() -> Unit = {},
    result: (Result?) -> Unit = {},
    cancel: () -> Unit = {}
): Job = lifecycleScope.launchWhenResumed {
    type.newInstance().also { dialog ->
        option.invoke(dialog)
        dialog.arguments = args
        dialog.show(fragmentManager, tag)
        dialog.result = result
        dialog.cancel = cancel
    }

}

fun <Dialog : BaseConfirmDialog<*, Result>, Result> Fragment.launchConfirmDialog(
    type: Class<Dialog>,
    args: Bundle? = null,
    tag: String? = null,
    fragmentManager: FragmentManager = childFragmentManager,
    option: Dialog.() -> Unit = {},
    result: (Result?) -> Unit = {},
    cancel: () -> Unit = {}
): Job = lifecycleScope.launchWhenResumed {
    type.newInstance().also { dialog ->
        option.invoke(dialog)
        dialog.arguments = args
        dialog.show(fragmentManager, tag)
        dialog.result = result
        dialog.cancel = cancel
    }

}