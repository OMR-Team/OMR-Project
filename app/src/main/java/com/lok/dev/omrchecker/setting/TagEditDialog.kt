package com.lok.dev.omrchecker.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.os.bundleOf
import com.lok.dev.commonbase.BaseDialogFragment
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.databinding.DialogTagEditBinding

class TagEditDialog : BaseDialogFragment<DialogTagEditBinding, Bundle>() {

    override var windowWidth = WRAP_CONTENT
    override var windowHeight = WRAP_CONTENT

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogTagEditBinding = DialogTagEditBinding.inflate(inflater, container, false)

    override fun initDialogFragment(savedInstanceState: Bundle?) {
        initView()
        addListener()
    }

    private fun initView() {
        binding.container.clipToOutline = true
        binding.tvHeader.text =
            if (arguments?.getString("cmd") == "AddTag") getString(R.string.tag_add_header)
            else getString(R.string.tag_edit_header)
        binding.btnCancel.clipToOutline = true
        binding.btnOk.clipToOutline = true
    }

    private fun addListener() = with(binding) {
        btnOk.setOnClickListener {
            result?.invoke(bundleOf("editText" to etTag.text.toString()))
            dismiss()
        }
        btnCancel.setOnClickListener {
            dismiss()
        }
    }

}