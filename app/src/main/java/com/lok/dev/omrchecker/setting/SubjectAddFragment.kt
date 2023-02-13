package com.lok.dev.omrchecker.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.lok.dev.commonbase.BaseFragment
import com.lok.dev.commonmodel.state.SubjectState
import com.lok.dev.omrchecker.databinding.FragmentSubjectAddBinding
import com.lok.dev.omrchecker.setting.viewmodel.SettingViewModel
import javax.inject.Inject

class SubjectAddFragment @Inject constructor() : BaseFragment<FragmentSubjectAddBinding>() {

    private val viewModel by activityViewModels<SettingViewModel>()

    override val enableBackPressed = true

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSubjectAddBinding.inflate(inflater, container, false)

    override fun onFragmentBackPressed() {
        viewModel.setSubjectState(SubjectState.Select)
    }
}