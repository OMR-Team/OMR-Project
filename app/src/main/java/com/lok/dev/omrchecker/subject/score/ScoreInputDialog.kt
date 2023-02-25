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
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.databinding.DialogScoreInputBinding
import com.lok.dev.omrchecker.subject.OmrViewModel

class ScoreInputDialog : BaseDialogFragment<DialogScoreInputBinding, Unit>() {

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = DialogScoreInputBinding.inflate(inflater, container, false)

    private var adapter : ScoreInputAdapter? = null

    private val viewModel: ScoreInputViewModel by viewModels()
    private val omrViewModel : OmrViewModel by activityViewModels()

    override fun initDialogFragment(savedInstanceState: Bundle?) {

        collectViewModel()
        initAdapter()
        adapter?.set(omrViewModel.answerInput.value)
        viewModel.scoreInputList = omrViewModel.answerInput.value.toMutableList()

        // TODO 확인 버튼 누르면 adapter 의 리스트 가져와서 db 에 저장
        setClickListeners()
    }

    private fun collectViewModel() = with(viewModel) {
        scoreState.onResult(lifecycleScope) { totalScore ->
            binding.totalScore.text = totalScore.toString()
        }
    }

    private fun initAdapter() = with(binding) {
        adapter = ScoreInputAdapter(requireContext()) {scoreData ->
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
            Log.d("123123123", "완료 버튼 눌림")
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