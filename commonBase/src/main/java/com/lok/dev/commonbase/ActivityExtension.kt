package com.lok.dev.commonbase

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
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