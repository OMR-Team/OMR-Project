package com.lok.dev.omrchecker.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.lok.dev.commonbase.BaseDialogFragment
import com.lok.dev.commonbase.util.launchDialogFragment
import com.lok.dev.commonutil.convertDpToPx
import com.lok.dev.commonutil.onResult
import com.lok.dev.commonutil.throttleFirstClick
import com.lok.dev.coredatabase.entity.OMRTable
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.databinding.FragmentSettingBinding
import com.lok.dev.omrchecker.setting.subject.SubjectDialog
import com.lok.dev.omrchecker.setting.viewmodel.SettingViewModel
import com.lok.dev.omrchecker.subject.OmrActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingDialog @Inject constructor() : BaseDialogFragment<FragmentSettingBinding, Bundle>() {

    private val viewModel by activityViewModels<SettingViewModel>()

    @Inject
    lateinit var subjectDialog: SubjectDialog

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
            showSubjectDialog()
        }

        throttleFirstClick(includeHeader.btnBack) {
            dismiss()
        }

        throttleFirstClick(txtTestStart) {
            val testName = etTitle.text.toString()
            val problemNum = etProblemNum.text.toString().toInt()
            val selectNum = spinner.selectedItem.toString().replace("개", "").toInt()
            viewModel.addOmrTest(testName, problemNum, selectNum) { omrTable ->
                startOmrActivity(omrTable)
                dismiss()
            }
        }
    }

    private fun collectViewModel() = with(viewModel) {
        subject.onResult(viewLifecycleOwner.lifecycleScope) {
            binding.txtSubjectTitle.text = it.name
        }
    }

    private fun showSubjectDialog() {
        launchDialogFragment(
            dialogFragment = subjectDialog,
            bottomSlideAnimation = true
        )
    }

    private fun startOmrActivity(omrTable: OMRTable) {
        val intent = Intent(requireContext(), OmrActivity::class.java)
        intent.putExtras(Bundle().apply {
            putParcelable("omrTable", omrTable)
        })
        this.startActivity(intent)
    }

}