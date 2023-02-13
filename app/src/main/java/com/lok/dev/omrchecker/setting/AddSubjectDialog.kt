package com.lok.dev.omrchecker.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.lok.dev.commonbase.BaseDialogFragment
import com.lok.dev.commonmodel.state.SubjectState
import com.lok.dev.omrchecker.databinding.DialogSubjectAddBinding

class AddSubjectDialog : BaseDialogFragment<DialogSubjectAddBinding, Bundle>() {

    private val viewModel by activityViewModels<SettingViewModel>()

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = DialogSubjectAddBinding.inflate(layoutInflater, container, false)

    override fun initDialogFragment(savedInstanceState: Bundle?) {
        addListeners()
    }

    private fun addListeners() = with(binding) {
        container.backgroundTouchDismiss()

        tvConfirm.setOnClickListener {
            viewModel.setSubjectState(SubjectState.Select)
            dismiss()
        }
    }
}