package com.lok.dev.omrchecker.setting.subject

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.lok.dev.commonbase.BaseFragment
import com.lok.dev.commonmodel.state.SubjectState
import com.lok.dev.commonutil.AppConfig
import com.lok.dev.commonutil.throttleFirstClick
import com.lok.dev.omrchecker.databinding.FragmentSubjectAddBinding
import com.lok.dev.omrchecker.setting.adapter.FolderAdapter
import com.lok.dev.omrchecker.setting.viewmodel.SubjectViewModel
import javax.inject.Inject

class SubjectAddFragment @Inject constructor() : BaseFragment<FragmentSubjectAddBinding>() {

    private val viewModel by activityViewModels<SubjectViewModel>()

    private val folderAdapter by lazy {
        FolderAdapter(requireContext())
    }

    override val enableBackPressed = true

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSubjectAddBinding.inflate(inflater, container, false)

    override fun initFragment() {
        setAdapter()
        addListeners()
    }

    private fun setAdapter() {
        binding.gridFolder.adapter = folderAdapter
        folderAdapter.set(AppConfig.folderData)
    }

    private fun addListeners() = with(binding) {
        throttleFirstClick(tvSave) {
            if(etSubjectTitle.text.isNotEmpty()) {
                folderAdapter.adapterList.firstOrNull { it.isSelect }?.let {
                    viewModel.addSubject(it.id, etSubjectTitle.text.toString())
                    viewModel.setSubjectState(SubjectState.List)
                }
            }
            else {
                Toast.makeText(requireContext(), "과목명을 입력하세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}