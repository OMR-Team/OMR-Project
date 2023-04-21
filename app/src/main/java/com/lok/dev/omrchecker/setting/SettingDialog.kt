package com.lok.dev.omrchecker.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.lok.dev.commonbase.BaseDialogFragment
import com.lok.dev.commonbase.util.launchConfirmDialog
import com.lok.dev.commonbase.util.launchDialogFragment
import com.lok.dev.commonmodel.CommonConstants
import com.lok.dev.commonmodel.CommonConstants.BUNDLE_SUBJECT_DATA
import com.lok.dev.commonutil.*
import com.lok.dev.coredatabase.entity.SubjectTable
import com.lok.dev.commonutil.convertDpToPx
import com.lok.dev.commonutil.onResult
import com.lok.dev.commonutil.throttleFirstClick
import com.lok.dev.coredatabase.entity.OMRTable
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.databinding.FragmentSettingBinding
import com.lok.dev.omrchecker.dialog.TitleConfirmDialog
import com.lok.dev.omrchecker.setting.subject.SubjectDialog
import com.lok.dev.omrchecker.setting.viewmodel.SettingViewModel
import com.lok.dev.omrchecker.subject.OmrActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingDialog : BaseDialogFragment<FragmentSettingBinding, Bundle>() {

    private val viewModel by viewModels<SettingViewModel>()

    override fun createFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentSettingBinding.inflate(layoutInflater, container, false)

    override fun onDialogBackPressed() {
        showDismissDialog()
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
            showDismissDialog()
        }

        throttleFirstClick(txtTestStart) {
            viewModel.getMaxId()
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
                val resId = requireContext().getDrawableString("folder_${data.fileName}") ?: R.drawable.folder_black
                binding.btnSubjectPlus.setImageResource(resId)
            }

            binding.txtSubjectTitle.text = it.name
            setEnableAddOmr()
        }

        maxId.onResult(viewLifecycleOwner.lifecycleScope) {
            val id = (it ?: 0) + 1
            val testName = binding.etTitle.text.toString()
            val problemNum = binding.etProblemNum.text.toString().toInt()
            val selectNum = binding.spinner.selectedItem.toString().replace("개", "").toInt()
            viewModel.addOmrTest(id, testName, problemNum, selectNum) { omrTable ->
                startOmrActivity(omrTable)
                dismiss()
            }
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

    private fun showDismissDialog() {
        launchConfirmDialog(
            type = TitleConfirmDialog::class.java,
            option = {
                title = this@SettingDialog.getString(R.string.omr_make_back_title)
                subTitle = this@SettingDialog.getString(R.string.omr_make_back_sub_title)
            },
            result = {
                dismiss()
            }
        )
    }

    private fun startOmrActivity(omrTable: OMRTable) {
        val intent = Intent(requireContext(), OmrActivity::class.java)
        intent.putExtras(Bundle().apply {
            putParcelable("omrTable", omrTable)
            putParcelable(BUNDLE_SUBJECT_DATA, viewModel.subjectData.value)
        })
        this.startActivity(intent)
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