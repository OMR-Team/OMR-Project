package com.lok.dev.omrchecker.setting.subject

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.lok.dev.commonbase.BaseFragment
import com.lok.dev.commonmodel.state.SubjectState
import com.lok.dev.commonutil.onUiState
import com.lok.dev.coredatabase.entity.SubjectTable
import com.lok.dev.omrchecker.databinding.FragmentSubjectListBinding
import com.lok.dev.omrchecker.setting.adapter.SubjectListAdapter
import com.lok.dev.omrchecker.setting.viewmodel.SubjectViewModel
import javax.inject.Inject

class SubjectListFragment @Inject constructor() : BaseFragment<FragmentSubjectListBinding>() {

    private val viewModel by activityViewModels<SubjectViewModel>()

    private val subjectListAdapter by lazy {
        SubjectListAdapter(requireContext(), viewLifecycleOwner.lifecycleScope, itemClick)
    }

    override val enableBackPressed = false

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSubjectListBinding.inflate(inflater, container, false)

    override fun initFragment() {
        setAdapter()
        addListeners()
        collectViewModel()
        viewModel.getSubjectList()
    }

    private fun setAdapter() {
        binding.rvSubjectList.adapter = subjectListAdapter
    }

    private fun addListeners() = with(binding) {
        layoutPlus.setOnClickListener {
            viewModel.setSubjectState(SubjectState.Add)
        }
    }

    private fun collectViewModel() = with(viewModel) {
        subjectListData.onUiState(viewLifecycleOwner.lifecycleScope,
            error = {
                Log.i(this::class.java.simpleName, it.printStackTrace().toString())
            },
            success = {
                subjectListAdapter.set(it)
            }
        )
    }

    private val itemClick: (SubjectTable) -> Unit = {
        viewModel.subjectData = it
        viewModel.setSubjectState(SubjectState.Exit)
    }

}