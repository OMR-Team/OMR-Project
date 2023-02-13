package com.lok.dev.omrchecker.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.lok.dev.commonbase.BaseDialogFragment
import com.lok.dev.commonbase.util.launchDialogFragment
import com.lok.dev.commonmodel.state.SubjectState
import com.lok.dev.commonutil.convertDpToPx
import com.lok.dev.commonutil.onResult
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingDialog @Inject constructor() : BaseDialogFragment<FragmentSettingBinding, Bundle>() {

    private val viewModel by activityViewModels<SettingViewModel>()

    @Inject
    lateinit var subjectDialog: SubjectDialog

    @Inject
    lateinit var addSubjectDialog: AddSubjectDialog

    override fun createFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentSettingBinding.inflate(layoutInflater, container, false)

    override fun onDialogBackPressed() {
        dismiss()
    }

    override fun initDialogFragment(savedInstanceState: Bundle?) {
        settingSpinner()
        addListeners()
        collectViewModel()
    }

    private fun settingSpinner() {
        val items = MutableList(9) { "${it + 2}개" }
        val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.item_spinner_select, items)
        binding.spinner.adapter = spinnerAdapter
        binding.spinner.setSelection(3)
        binding.spinner.dropDownVerticalOffset = requireContext().convertDpToPx(40f).toInt()
    }

    private fun addListeners() = with(binding) {
        layoutSubjectPlus.setOnClickListener {
            viewModel.setSubjectState(SubjectState.Select)
        }
    }

    private fun collectViewModel() = with(viewModel) {
        subjectState.onResult(lifecycleScope) {
            when(it) {
                SubjectState.Select -> {
                    launchDialogFragment(
                        dialogFragment = subjectDialog,
                        bottomSlideAnimation = true
                    )
                }
                SubjectState.Add -> {
                    launchDialogFragment(
                        dialogFragment = addSubjectDialog,
                        bottomSlideAnimation = true
                    )
                }
                else -> {}
            }
        }
    }
}