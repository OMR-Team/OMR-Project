package com.lok.dev.omrchecker.subject.result.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.lok.dev.commonbase.BaseFragment
import com.lok.dev.commonbase.util.launchDialogFragment
import com.lok.dev.commonutil.convertDpToPx
import com.lok.dev.commonutil.showToast
import com.lok.dev.commonutil.spacing
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.databinding.FragmentResultScoreBinding
import com.lok.dev.omrchecker.subject.OmrViewModel
import com.lok.dev.omrchecker.subject.result.ResultAnswerDialog
import com.lok.dev.omrchecker.subject.result.ResultViewModel
import com.lok.dev.omrchecker.subject.result.adapter.ResultListAdapter

class ResultScoreFragment : BaseFragment<FragmentResultScoreBinding>() {

    private val viewModel: ResultViewModel by activityViewModels()
    private val omrViewModel: OmrViewModel by activityViewModels()

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
        setListeners()
        collectViewModel()
    }

    private fun settingSpinner(count: Int) {
        val items = MutableList(count) { "${it + 1}회차" }
        val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.item_spinner_select, items)
        binding.testNumSpinner.apply {
            adapter = spinnerAdapter
            setSelection(items.lastIndex)
            dropDownVerticalOffset = requireContext().convertDpToPx(7f).toInt()
            dropDownWidth = requireContext().convertDpToPx(100f).toInt()
        }
    }

    private fun initAdapter() = with(binding) {
        resultList.adapter = listAdapter
        resultList.layoutManager = GridLayoutManager(requireContext(), 5)
        resultList.spacing()
    }

    private fun setListeners() = with(binding) {
        testNumSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.getResultJoin(omrViewModel.tableData.id, position + 1)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }

    private fun collectViewModel() = with(viewModel) {
        resultJoinState.onUiState(
            success = { list ->
                listAdapter.set(list)
            },
            error = {
                requireContext().showToast(R.string.omr_error_result_answer)
            }
        )

        historyState.onUiState(
            success = { list ->
                viewModel.historyList = list
                settingSpinner(list.count())
            }
        )
    }
}