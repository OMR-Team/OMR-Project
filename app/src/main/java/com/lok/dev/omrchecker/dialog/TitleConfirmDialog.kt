package com.lok.dev.omrchecker.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lok.dev.commonbase.BaseConfirmDialog
import com.lok.dev.commonutil.visible
import com.lok.dev.omrchecker.databinding.DialogTitleConfirmBinding

class TitleConfirmDialog : BaseConfirmDialog<DialogTitleConfirmBinding, Bundle>() {

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = DialogTitleConfirmBinding.inflate(inflater, container, false)

    var title: String = ""
    var subTitle: String = ""

    override fun initDialogFragment(savedInstanceState: Bundle?) = with(binding) {

        titleText.text = title
        subTitleText.text = subTitle

        titleText.visible(title.isNotEmpty())
        subTitleText.visible(subTitle.isNotEmpty())

        btnCancel.text = requireContext().getText(cancelText)
        btnConfirm.text = requireContext().getText(confirmText)

        btnCancel.visible(isCancelBtnVisible)

        btnCancel.setOnClickListener { dismiss() }
        btnConfirm.setOnClickListener { setResultOnDismiss(Bundle()) }

    }

}