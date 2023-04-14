package com.lok.dev.omrchecker.subject.result.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.lok.dev.commonbase.BaseFragment
import com.lok.dev.commonbase.util.launchDialogFragment
import com.lok.dev.commonutil.onUiState
import com.lok.dev.commonutil.showToast
import com.lok.dev.commonutil.spacing
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.databinding.FragmentResultScoreBinding
import com.lok.dev.omrchecker.subject.result.ResultAnswerDialog
import com.lok.dev.omrchecker.subject.result.adapter.ResultListAdapter
import com.lok.dev.omrchecker.subject.result.ResultViewModel

class ResultScoreFragment : BaseFragment<FragmentResultScoreBinding>() {

    private val viewModel: ResultViewModel by activityViewModels()

    private val listAdapter by lazy {
        ResultListAdapter(requireContext(), viewLifecycleOwner.lifecycleScope) {
            launchDialogFragment(
                dialogFragment = ResultAnswerDialog().apply {
                    resultData = it
                },
                bottomSlideAnimation = true
            )
        }
    }

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentResultScoreBinding.inflate(inflater, container, false)

    override fun initFragment() {
        initAdapter()
        collectViewModel()
    }

    private fun initAdapter() = with(binding) {
        resultList.adapter = listAdapter
        resultList.layoutManager = GridLayoutManager(requireContext(), 5)
        resultList.spacing()
    }

    private fun collectViewModel() = with(viewModel) {
        resultJoinState.onUiState(lifecycleScope,
            success = { list ->
                listAdapter.set(list)
            },
            error = {
                requireContext().showToast(R.string.omr_error_result_answer)
            }
        )
    }
}