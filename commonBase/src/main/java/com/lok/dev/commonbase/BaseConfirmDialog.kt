package com.lok.dev.commonbase

import android.os.Bundle
import android.view.ViewGroup.LayoutParams
import androidx.annotation.StringRes
import androidx.databinding.ViewDataBinding
import com.lok.dev.commonBase.R

abstract class BaseConfirmDialog<Binding : ViewDataBinding, Result> : BaseDialogFragment<Binding, Result>() {

    init {
        dimBehind = true
    }

    var message: String = ""
    var subMessage: String = ""

    @StringRes
    var confirmText: Int = R.string.text_ok

    @StringRes
    var cancelText: Int = R.string.text_cancel
    var isCancelBtnVisible: Boolean = true


    override fun initDialogFragment(savedInstanceState: Bundle?) {
        canceledOnTouchOutside = false
        dialog?.setCanceledOnTouchOutside(canceledOnTouchOutside)
    }

}