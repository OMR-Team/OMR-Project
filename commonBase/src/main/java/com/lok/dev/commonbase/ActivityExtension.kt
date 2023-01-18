package com.lok.dev.commonbase

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job

fun <Dialog : BaseDialogFragment<*, Result>, Result> FragmentActivity.launchDialog(
    type: Class<Dialog>,
    args: Bundle = bundleOf(),
    tag: String? = null,
    success: (Result?) -> Unit = {},
    cancel: () -> Unit = {}
): Job = lifecycleScope.launchWhenResumed {
    type.newInstance().also { dialog ->
        dialog.arguments = args
        dialog.show(supportFragmentManager, tag)
        dialog.success = success
        dialog.cancel = cancel
    }
}

// BottomSheetDialogFragment 런처
fun <Binding : ViewDataBinding, Result> FragmentActivity.launchDialogFragment(
    dialogFragment: BaseBottomSheetDialogFragment<Binding, Result>,
    fragmentManager: FragmentManager = supportFragmentManager,
    tag: String? = null,
    result: (Result?) -> Unit = {},
    cancel: () -> Unit = {}
): Job = lifecycleScope.launchWhenStarted {
    dialogFragment.result = result
    dialogFragment.cancel = cancel
    dialogFragment.show(fragmentManager, tag)
}