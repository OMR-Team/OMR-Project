package com.lok.dev.omrchecker.subject.result

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import com.lok.dev.commonbase.BaseFragment
import com.lok.dev.commonmodel.CommonConstants
import com.lok.dev.commonutil.onResult
import com.lok.dev.commonutil.onUiState
import com.lok.dev.commonutil.showToast
import com.lok.dev.commonutil.throttleFirstClick
import com.lok.dev.coredatabase.entity.OMRTable
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.databinding.FragmentResultBinding
import com.lok.dev.omrchecker.subject.OmrActivity
import com.lok.dev.omrchecker.subject.OmrViewModel
import com.lok.dev.omrchecker.subject.result.adapter.ResultViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ResultFragment @Inject constructor() : BaseFragment<FragmentResultBinding>() {

    private val viewModel: ResultViewModel by activityViewModels()
    private val omrViewModel: OmrViewModel by activityViewModels()

    private val viewPagerAdapter by lazy {
        ResultViewPagerAdapter(requireContext(), requireActivity())
    }

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentResultBinding.inflate(inflater, container, false)

    override fun initFragment() {
        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })
        initAdapter()
        collectViewModel()
        setOnClickListeners()
        viewModel.getHistoryData(omrViewModel.tableData.id)
    }

    private fun initAdapter() = with(binding) {
        val tabTitleArray = listOf("답안 체크", "그래프")
        viewPager.adapter = viewPagerAdapter
        viewPager.offscreenPageLimit = 2
        viewPagerAdapter.set(tabTitleArray)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()

    }

    private fun collectViewModel() = with(viewModel) {
        historyState.onUiState(lifecycleScope,
            success = { list ->
                binding.txtScoreMax.text = list.maxOf { it.score }.toString()
            },
            error = {
                requireContext().showToast(R.string.omr_error_result_answer)
            }
        )

        resultJoinState.onUiState(lifecycleScope,
            success = { list ->
                viewModel.historyList.firstOrNull { it.cnt == list.first().cnt }?.let {
                    binding.progress.setProgressAnimated((it.score / it.totalScore * 100).toFloat())
                    binding.txtScore.text = it.score.toString()
                    binding.txtScoreTotal.text = "/ ${it.totalScore}"
                    binding.txtScore2.text = it.score.toString()
                }
            },
            error = {
                requireContext().showToast(R.string.omr_error_result_answer)
            }
        )
    }

    private fun setOnClickListeners() = with(binding) {
        throttleFirstClick(btnReSet) {
            omrViewModel.addOmrTest {
                startOmrActivity(it)
                requireActivity().finish()
            }
        }
    }

    private fun startOmrActivity(omrTable: OMRTable) {
        val intent = Intent(requireContext(), OmrActivity::class.java)
        intent.putExtras(Bundle().apply {
            putParcelable("omrTable", omrTable)
            putParcelable(CommonConstants.BUNDLE_SUBJECT_DATA, omrViewModel.subjectData)
        })
        this.startActivity(intent)
    }
}