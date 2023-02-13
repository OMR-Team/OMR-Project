package com.lok.dev.omrchecker.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.lok.dev.commonbase.BaseFragment
import com.lok.dev.commonmodel.state.SubjectState
import com.lok.dev.omrchecker.databinding.FragmentSubjectListBinding
import com.lok.dev.omrchecker.setting.viewmodel.SettingViewModel
import javax.inject.Inject

class SubjectListFragment @Inject constructor() : BaseFragment<FragmentSubjectListBinding>() {

    private val viewModel by activityViewModels<SettingViewModel>()

    override val enableBackPressed = false

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSubjectListBinding.inflate(inflater, container, false)

    override fun initFragment() {
        addListeners()
    }

    private fun addListeners() = with(binding) {
        layoutPlus.setOnClickListener {
            viewModel.setSubjectState(SubjectState.Add)
        }
    }

}