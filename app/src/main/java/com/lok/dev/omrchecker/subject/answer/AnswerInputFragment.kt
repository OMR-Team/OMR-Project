package com.lok.dev.omrchecker.subject.answer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.lok.dev.commonbase.BaseFragment
import com.lok.dev.commonbase.util.launchDialogFragment
import com.lok.dev.commonutil.getChangeTextStyle
import com.lok.dev.commonutil.onResult
import com.lok.dev.commonutil.throttleFirstClick
import com.lok.dev.coredatabase.entity.AnswerTable
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.databinding.FragmentAnswerInputBinding
import com.lok.dev.omrchecker.subject.OmrViewModel
import com.lok.dev.omrchecker.subject.score.ScoreInputDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AnswerInputFragment @Inject constructor() : BaseFragment<FragmentAnswerInputBinding>() {

    private var adapter: AnswerInputAdapter? = null
    private val viewModel: AnswerInputViewModel by viewModels()
    private val omrViewModel: OmrViewModel by activityViewModels()

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAnswerInputBinding.inflate(inflater, container, false)

    override fun initFragment() {
        initData()
        collectViewModel()
        setOnClickListeners()
        initAdapter()
        setBody()
    }

    private fun initData() {
        if(omrViewModel.isTemp) omrViewModel.getAnswerTable()
        else makeAnswerTable()
    }

    private fun collectViewModel() {
        omrViewModel.answerProgressState.onResult(viewLifecycleOwner.lifecycleScope) { progress ->
            val text = String.format(getString(R.string.omr_input_cnt), progress, omrViewModel.tableData.problemNum)
            binding.omrAnswerCnt.text = requireActivity().getChangeTextStyle(text, progress.toString(), R.color.theme_red)
        }

        omrViewModel.saveInputData.onResult(viewLifecycleOwner.lifecycleScope) {
            viewModel.addAnswerTable(adapter?.adapterList ?: mutableListOf())
        }

    }

    private fun initAdapter() {
        adapter = AnswerInputAdapter(requireContext()) { pair ->
            omrViewModel.updateAnswerProgress(pair)
        }
        binding.omrAnswerList.adapter = adapter
        adapter?.set(omrViewModel.answerInput.value)
    }

    private fun setOnClickListeners() = with(binding) {

        throttleFirstClick(omrScoreBtn) {
            launchDialogFragment(
                dialogFragment = ScoreInputDialog(),
                result = { scoreList ->
                    if (!scoreList.isNullOrEmpty()) viewModel.scoreList.addAll(scoreList)
                }
            )
        }

    }

    private fun setBody() = with(binding) {
        val text = String.format(getString(R.string.omr_input_cnt), 0, omrViewModel.tableData.problemNum)
        binding.omrAnswerCnt.text = requireActivity().getChangeTextStyle(text, "0", R.color.theme_red)
    }

    private fun makeAnswerTable() {
        val answerInputList = arrayListOf<AnswerTable>()
        val answerList = List(omrViewModel.tableData.selectNum) { 0 }
        for (i in 0 until omrViewModel.tableData.problemNum) {
            answerInputList.add(AnswerTable(omrViewModel.tableData.id, i.plus(1), answerList, null))
        }
        omrViewModel.changeAnswerInput(answerInputList)
    }
}