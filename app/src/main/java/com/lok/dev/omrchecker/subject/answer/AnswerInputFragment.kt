package com.lok.dev.omrchecker.subject.answer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
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
    private val omrViewModel: OmrViewModel by activityViewModels()

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAnswerInputBinding.inflate(inflater, container, false)

    override fun initFragment() {

        // TODO 확인용 임시 데이터
        val test = arrayListOf<AnswerTable>()
        val answerList = List(omrViewModel.answerNum){ 0 }
        for (i in 0 until omrViewModel.problemNum) {
            test.add(AnswerTable(i, i+1, answerList, 0.0))
        }
        omrViewModel.changeAnswerInput(test)

        collectViewModel()
        setOnClickListeners()
        initAdapter()
        setBody()

    }

    private fun collectViewModel() {
        omrViewModel.answerProgressState.onResult(viewLifecycleOwner.lifecycleScope) { progress ->
            val text = String.format(getString(R.string.omr_input_cnt), progress, omrViewModel.answerNum)
            binding.omrAnswerCnt.text = requireActivity().getChangeTextStyle(text, progress.toString(), R.color.theme_red)
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
                dialogFragment = ScoreInputDialog()
            )
        }

    }

    private fun setBody() = with(binding) {
        val text = String.format(getString(R.string.omr_input_cnt), 0, omrViewModel.problemNum)
        binding.omrAnswerCnt.text = requireActivity().getChangeTextStyle(text, "0", R.color.theme_red)
    }
}