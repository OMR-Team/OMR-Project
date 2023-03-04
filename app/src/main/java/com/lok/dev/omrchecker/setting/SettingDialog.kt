package com.lok.dev.omrchecker.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.lok.dev.commonbase.BaseDialogFragment
import com.lok.dev.commonbase.util.launchDialogFragment
import com.lok.dev.commonmodel.CommonConstants
import com.lok.dev.commonutil.*
import com.lok.dev.coredatabase.entity.SubjectTable
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.databinding.FragmentSettingBinding
import com.lok.dev.omrchecker.setting.subject.SubjectDialog
import com.lok.dev.omrchecker.setting.viewmodel.SettingViewModel
import com.lok.dev.omrchecker.subject.OmrActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingDialog : BaseDialogFragment<FragmentSettingBinding, Bundle>() {

    companion object {
        const val OMR_SUBJECT_NAME = "subjectName"
        const val OMR_TEST_NAME = "testName"
        const val OMR_PROBLEM_NUM = "problemNum"
        const val OMR_ANSWER_NUM = "answerNum"
    }

    private val viewModel by viewModels<SettingViewModel>()

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
            val subjectName = viewModel.subjectData.value.name
            val testName = etTitle.text.toString()
            val problemNum = etProblemNum.text.toString().toInt()
            val selectNum = spinner.selectedItem.toString().replace("개", "").toInt()
            viewModel.addOmrTest(testName, problemNum, selectNum) {
                it
            }
            startOmrActivity(subjectName, testName, problemNum, selectNum)
            dismiss()
        }

        throttleFirstClick(chipTag) {
            launchDialogFragment(
                dialogFragment = TagDialog(),
                bottomSlideAnimation = true
            )
        }

        etTitle.addTextChangedListener {
            setEnableAddOmr()
        }

        etProblemNum.addTextChangedListener {
            setEnableAddOmr()
        }
    }

    private fun collectViewModel() = with(viewModel) {
        subjectData.onResult(viewLifecycleOwner.lifecycleScope) {
            AppConfig.folderData.firstOrNull { data -> data.id == it.folderId }?.let { data ->
                binding.btnSubjectPlus.setImageResource(requireContext().getDrawableString("folder_${data.fileName}"))
            }

            binding.txtSubjectTitle.text = it.name
            setEnableAddOmr()
        }
    }

    private fun showSubjectDialog() {
        launchDialogFragment(
            dialogFragment = SubjectDialog(),
            bottomSlideAnimation = true,
            result = {
                it?.safeParcelable<SubjectTable>(CommonConstants.BUNDLE_SUBJECT_DATA)?.let { subject ->
                    viewModel.setSubject(subject)
                }
            }
        )
    }

    private fun startOmrActivity(subjectName: String, testName: String, problemNum: Int, selectNum: Int) {
        val intent = Intent(requireContext(), OmrActivity::class.java)
        intent.putExtras(
            bundleOf (
                "type" to "omr",
                OMR_SUBJECT_NAME to subjectName,
                OMR_TEST_NAME to testName,
                OMR_PROBLEM_NUM to problemNum,
                OMR_ANSWER_NUM to selectNum
            )
        )
        startActivity(intent)
    }

    private fun setEnableAddOmr() {
        val problemNum = binding.etProblemNum.text.toString()
        binding.txtTestStart.isEnabled = (
                viewModel.subjectData.value.id != 0
                        && binding.etTitle.text.isNotEmpty()
                        && problemNum.isNotEmpty()
                        && problemNum.toInt() > CommonConstants.PROBLEM_NUM_MIN
                        && problemNum.toInt() <= CommonConstants.PROBLEM_NUM_MAX
                )
    }
}