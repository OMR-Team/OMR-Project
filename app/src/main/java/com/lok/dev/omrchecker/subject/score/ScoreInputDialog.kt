package com.lok.dev.omrchecker.subject.score

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.lok.dev.commonbase.BaseDialogFragment
import com.lok.dev.commonutil.onResult
import com.lok.dev.commonutil.showToast
import com.lok.dev.commonutil.throttleFirstClick
import com.lok.dev.coredatabase.entity.AnswerTable
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.databinding.DialogScoreInputBinding
import com.lok.dev.omrchecker.subject.OmrViewModel
import com.lok.dev.omrchecker.subject.answer.AnswerInputViewModel

class ScoreInputDialog : BaseDialogFragment<DialogScoreInputBinding, List<AnswerTable>>() {

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = DialogScoreInputBinding.inflate(inflater, container, false)

    private var adapter : ScoreInputAdapter? = null

    private val viewModel: ScoreInputViewModel by viewModels()
    private val answerViewModel : AnswerInputViewModel by viewModels({requireParentFragment()})
    private val omrViewModel : OmrViewModel by activityViewModels()

    override fun initDialogFragment(savedInstanceState: Bundle?) {

        collectViewModel()
        initAdapter()
        adapter?.set(answerViewModel.answerList)
        viewModel.scoreInputList.addAll(answerViewModel.answerList)

        setClickListeners()
    }

    private fun collectViewModel() = with(viewModel) {
        scoreState.onResult(lifecycleScope) { totalScore ->
            binding.totalScore.text = totalScore.toString()
        }
    }

    private fun initAdapter() = with(binding) {
        adapter = ScoreInputAdapter(requireContext(), lifecycleScope) {scoreData ->
            viewModel.updateScore(scoreData)
        }
        scoreList.layoutManager = GridLayoutManager(requireContext(), 5)
        scoreList.adapter = adapter
    }

    private fun setClickListeners() = with(binding) {

        throttleFirstClick(btnBack) {
            dismiss()
        }

        throttleFirstClick(btnComplete) {
            if(!adapter?.adapterList.isNullOrEmpty()) omrViewModel.hasScore = true
            setResultOnDismiss(adapter?.adapterList ?: mutableListOf())
        }

        throttleFirstClick(scoreEraseAll) {
            viewModel.changeAllScore(0.0)
            adapter?.set(viewModel.scoreInputList)
            editScoreInputAll.setText("")
            hideKeyboard(editScoreInputAll)
        }

        throttleFirstClick(scoreConfirmAll) {
            val score = editScoreInputAll.text.toString()
            if(score.isNotEmpty()) {
                viewModel.changeAllScore(score.toDouble())
                adapter?.set(viewModel.scoreInputList)
                hideKeyboard(editScoreInputAll)
            }else {
                requireContext().showToast(R.string.omr_score_input_all_warn)
            }
        }

    }

    private fun hideKeyboard(view: EditText) {
        view.clearFocus()
        val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onDialogBackPressed() {
        dismiss()
    }

}