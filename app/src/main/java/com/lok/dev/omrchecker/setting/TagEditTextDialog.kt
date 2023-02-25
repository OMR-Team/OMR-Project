package com.lok.dev.omrchecker.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.os.bundleOf
import com.lok.dev.commonbase.BaseDialogFragment
import com.lok.dev.commonmodel.CommonConstants.CMD_ADD_TAG
import com.lok.dev.commonmodel.CommonConstants.TAG_NAME_IN_EDIT_TEXT
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.databinding.DialogTagEditTextBinding

class TagEditTextDialog : BaseDialogFragment<DialogTagEditTextBinding, Bundle>() {

    init {
        windowWidth = WRAP_CONTENT
        windowHeight = WRAP_CONTENT
        dimBehind = true
    }

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogTagEditTextBinding = DialogTagEditTextBinding.inflate(inflater, container, false)

    override fun initDialogFragment(savedInstanceState: Bundle?) {
        initView()
        addListener()
    }

    private fun initView() {
        binding.container.clipToOutline = true
        binding.tvHeader.text =
            if (arguments?.getString("cmd") == CMD_ADD_TAG) getString(R.string.tag_edit_text_add)
            else getString(R.string.tag_edit_text_edit)
        binding.btnCancel.clipToOutline = true
        binding.btnOk.clipToOutline = true
    }

    private fun addListener() = with(binding) {
        btnOk.setOnClickListener {
            result?.invoke(bundleOf(TAG_NAME_IN_EDIT_TEXT to etTag.text.toString()))
            dismiss()
        }
        btnCancel.setOnClickListener {
            dismiss()
        }
    }

}