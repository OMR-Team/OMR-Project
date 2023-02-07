package com.lok.dev.omrchecker.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lok.dev.commonbase.BaseDialogFragment
import com.lok.dev.commonbase.util.launchDialogFragment
import com.lok.dev.omrchecker.databinding.FragmentSettingBinding
import javax.inject.Inject

class SettingDialog @Inject constructor() : BaseDialogFragment<FragmentSettingBinding, Bundle>() {

    @Inject
    lateinit var subjectDialog: SubjectDialog

    override fun createFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentSettingBinding.inflate(layoutInflater, container, false)

    override fun onDialogBackPressed() {
        dismiss()
    }

    override fun initDialogFragment(savedInstanceState: Bundle?) {
        addListeners()
    }

    private fun addListeners() = with(binding) {
        layoutSubjectPlus.setOnClickListener {
            launchDialogFragment(
                dialogFragment = SubjectDialog()
            )
        }
    }
}