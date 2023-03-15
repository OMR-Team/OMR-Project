package com.lok.dev.omrchecker.subject.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.lok.dev.commonbase.BaseFragment
import com.lok.dev.omrchecker.databinding.FragmentResultBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ResultFragment @Inject constructor() : BaseFragment<FragmentResultBinding>() {

    private val viewPagerAdapter by lazy {
        ResultViewPagerAdapter(requireContext(), requireActivity())
    }

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentResultBinding.inflate(inflater, container, false)

    private var adapter : ResultAdapter? = null

    override fun initFragment() {

        initAdapter()

    }

    private fun initAdapter() = with(binding) {

        /*adapter = ResultAdapter(requireContext(), viewLifecycleOwner.lifecycleScope)
        resultList.layoutManager = GridLayoutManager(requireContext(), 5)
        resultList.adapter = adapter*/

        progress.setProgressAnimated(60f)

        val tabTitleArray = listOf("답안 체크", "그래프")
        viewPager.adapter = viewPagerAdapter
        viewPagerAdapter.set(tabTitleArray)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()

    }

}