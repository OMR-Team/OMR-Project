package com.lok.dev.omrchecker.subject.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.lok.dev.commonbase.BaseFragment
import com.lok.dev.omrchecker.databinding.FragmentResultBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ResultFragment @Inject constructor() : BaseFragment<FragmentResultBinding>() {
    
    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentResultBinding.inflate(inflater, container, false)

    private var adapter : ResultAdapter? = null

    override fun initFragment() {

        initAdapter()

    }

    private fun initAdapter() = with(binding) {

        adapter = ResultAdapter(requireContext(), viewLifecycleOwner.lifecycleScope)
        resultList.layoutManager = GridLayoutManager(requireContext(), 5)
        resultList.adapter = adapter

    }

}