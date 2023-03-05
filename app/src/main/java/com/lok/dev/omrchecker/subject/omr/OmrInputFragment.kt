package com.lok.dev.omrchecker.subject.omr

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.lok.dev.commonbase.BaseFragment
import com.lok.dev.commonutil.getChangeTextStyle
import com.lok.dev.commonutil.onResult
import com.lok.dev.coredatabase.entity.ProblemTable
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.databinding.FragmentOmrInputBinding
import com.lok.dev.omrchecker.subject.OmrViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OmrInputFragment @Inject constructor() : BaseFragment<FragmentOmrInputBinding>() {

    private var adapter: OmrInputAdapter? = null

    private val viewModel: OmrInputViewModel by viewModels()
    private val omrViewModel: OmrViewModel by activityViewModels()

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentOmrInputBinding.inflate(inflater, container, false)

    override fun initFragment() {

        collectViewModel()
        initAdapter()
        setScreen()

    }

    private fun collectViewModel() {
        omrViewModel.progressState.onResult(viewLifecycleOwner.lifecycleScope) { progress ->
            val text = String.format(getString(R.string.omr_input_cnt), progress, omrViewModel.tableData.problemNum)
            binding.omrInputCnt.text = requireActivity().getChangeTextStyle(text, progress.toString(), R.color.theme_blue_primary)
        }

        omrViewModel.saveInputData.onResult(viewLifecycleOwner.lifecycleScope) {
            viewModel.addProblemTable(adapter?.adapterList ?: mutableListOf())
        }

        omrViewModel.omrInput.onResult(viewLifecycleOwner.lifecycleScope) { problemList ->
            adapter?.set(problemList)
            if(omrViewModel.isTemp) {
                updateProgress(problemList)
                omrViewModel.isTemp = false
            }
        }

    }

    private fun initAdapter() {
        adapter = OmrInputAdapter(requireContext(), viewLifecycleOwner.lifecycleScope) { pair ->
            omrViewModel.updateProblemProgress(pair)
        }
        binding.omrInputList.adapter = adapter
    }

    private fun setScreen() = with(binding) {
        val text = String.format(getString(R.string.omr_input_cnt), 0, omrViewModel.tableData.problemNum)
        omrInputCnt.text = requireActivity().getChangeTextStyle(text, "0", R.color.theme_blue_primary)
        if(omrViewModel.isTemp) omrViewModel.getProblemTable()
        else omrViewModel.makeProblemTable()
    }

    /** 임시저장 불러오기 후, 진행바 업데이트 **/
    private fun updateProgress(list: List<ProblemTable>) {
        list.forEach {
            omrViewModel.updateProblemProgress(Pair(it.answer.any { num -> num != 0 } , it.answer.count { num -> num != 0 }))
        }
    }

}