package com.lok.dev.omrchecker.subject.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.lok.dev.commonbase.BaseFragment
import com.lok.dev.commonutil.onResult
import com.lok.dev.commonutil.onUiState
import com.lok.dev.commonutil.showToast
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.databinding.FragmentResultBinding
import com.lok.dev.omrchecker.subject.OmrViewModel
import com.lok.dev.omrchecker.subject.omr.OmrInputViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ResultFragment @Inject constructor() : BaseFragment<FragmentResultBinding>() {

    private val viewModel: ResultViewModel by viewModels()
    private val omrViewModel: OmrViewModel by activityViewModels()

    private val viewPagerAdapter by lazy {
        ResultViewPagerAdapter(requireContext(), requireActivity())
    }

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentResultBinding.inflate(inflater, container, false)

    private var adapter : ResultAdapter? = null

    override fun initFragment() {
        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })
        initAdapter()
        collectViewModel()
        viewModel.getHistoryData(omrViewModel.tableData.id, omrViewModel.tableData.cnt)
        viewModel.getResultJoin(omrViewModel.tableData.id, omrViewModel.tableData.cnt)
    }

    private fun initAdapter() = with(binding) {

        /*adapter = ResultAdapter(requireContext(), viewLifecycleOwner.lifecycleScope)
        resultList.layoutManager = GridLayoutManager(requireContext(), 5)
        resultList.adapter = adapter*/

        val tabTitleArray = listOf("답안 체크", "그래프")
        viewPager.adapter = viewPagerAdapter
        viewPagerAdapter.set(tabTitleArray)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()

    }

    private fun collectViewModel() = with(viewModel) {
        resultJoinState.onUiState(lifecycleScope,
            success = { list ->
            },
            error = {
                requireContext().showToast(R.string.omr_error_result_answer)
            }
        )

        historyState.onUiState(lifecycleScope,
            success = {
                binding.progress.setProgressAnimated((it.score / it.totalScore * 100).toFloat())
                binding.txtScore.text = it.score.toString()
                binding.txtScoreTotal.text = "/${it.totalScore}"
            },
            error = {
                requireContext().showToast(R.string.omr_error_result_answer)
            }
        )
    }

}