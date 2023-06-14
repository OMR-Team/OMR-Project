package com.lok.dev.omrchecker.subject.answer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.lok.dev.commonbase.BaseFragment
import com.lok.dev.commonbase.util.launchDialogFragment
import com.lok.dev.commonutil.getChangeTextStyle
import com.lok.dev.commonutil.throttleFirstClick
import com.lok.dev.coredatabase.entity.AnswerTable
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.databinding.FragmentAnswerInputBinding
import com.lok.dev.omrchecker.subject.OmrActivity
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
        collectViewModel()
        setOnClickListeners()
        initAdapter()
        setScreen()
    }

    private fun collectViewModel() {
        omrViewModel.answerProgressState.onResult { progress ->
            val text = String.format(getString(R.string.omr_input_cnt), progress, omrViewModel.tableData.problemNum)
            binding.omrAnswerCnt.text = requireActivity().getChangeTextStyle(text, progress.toString(), R.color.theme_red)
        }

        omrViewModel.saveInputData.onResult {
            val answerTable = viewModel.convertToAnswerTable(omrViewModel.tableData.id)
            omrViewModel.answerTable = viewModel.getAnswerList(answerTable)
            viewModel.addAnswerTable(omrViewModel.answerTable)
            omrViewModel.updateOMRTable(false, OmrActivity.PAGE_RESULT)
        }

        viewModel.answerInput.onResult { answerList ->
            viewModel.convertToAnswerList(answerList, omrViewModel.tableData.selectNum)
            updateProgress(answerList)
            omrViewModel.isTemp = false
            if(answerList.count { it.score == 0.0 } == 0) omrViewModel.hasScore = true
        }

        viewModel.tempAnswerInputState.onResult {
            if(it.isNotEmpty()) {
                viewModel.changeAnswerInput(it)
            }
            else {
                makeAnswerTable()
            }
        }

        viewModel.answerList.onResult {
            adapter?.setList(it)
        }
    }

    private fun initAdapter() {
        adapter = AnswerInputAdapter(
            requireContext(),
            viewLifecycleOwner.lifecycleScope,
            omrViewModel.tableData.selectNum
        ) { changeData ->
            val problemNum = changeData.first   // 문제 번호
            val answerNum = changeData.second   // 답안 번호
            val isSelected = changeData.third   // 선택 여부
            viewModel.updateAnswerList(problemNum, answerNum, isSelected)
            omrViewModel.updateAnswerProgress(problemNum, isSelected)
        }
        binding.omrAnswerList.adapter = adapter
    }

    private fun setOnClickListeners() = with(binding) {

        throttleFirstClick(omrScoreBtn) {
            launchDialogFragment(
                dialogFragment = ScoreInputDialog(),
                result = { scoreList ->
                    if (!scoreList.isNullOrEmpty()) {
                        viewModel.scoreList.clear()
                        viewModel.scoreList.addAll(scoreList)
                    }
                }
            )
        }

    }

    private fun setScreen() = with(binding) {
        val text = String.format(getString(R.string.omr_input_cnt), 0, omrViewModel.tableData.problemNum)
        binding.omrAnswerCnt.text = requireActivity().getChangeTextStyle(text, "0", R.color.theme_red)
        viewModel.getAnswerTable(omrViewModel.tableData.id)
    }

    private fun makeAnswerTable() {
        val answerInputList = mutableListOf<AnswerTable>()
        val answerList = mutableListOf<Int>()
        for (i in 1 .. omrViewModel.tableData.problemNum) {
            answerInputList.add(AnswerTable(omrViewModel.tableData.id, i, answerList, 0.0))
        }
        viewModel.changeAnswerInput(answerInputList)
    }

    /** 임시저장 불러오기 후, 진행바 업데이트 **/
    private fun updateProgress(list: List<AnswerTable>) {
        list.forEach { answerTable ->
            repeat(answerTable.answer.size) {
                omrViewModel.updateAnswerProgress(answerTable.no, true)
            }
        }
    }
}