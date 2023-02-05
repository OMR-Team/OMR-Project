package com.lok.dev.omrchecker.subject.omr

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.lok.dev.commonbase.BaseFragment
import com.lok.dev.omrchecker.databinding.FragmentOmrInputBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OmrInputFragment @Inject constructor() : BaseFragment<FragmentOmrInputBinding>() {

    private var adapter : OmrInputAdapter? = null

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentOmrInputBinding.inflate(inflater, container, false)

    override fun initFragment() {

        initAdapter()
        setOnClickListeners()


    }

    private fun initAdapter() {

        // activity 의 viewModel 에서 받아오는 걸로 변경
        // 임시저장일 때와 아닐떄로 분기처리
        // 임시저장일때는 임시저장했던 리스트를 가져와서 array 를 만들어줘야하고
        // 임시저장이 아닐때는 그냥 만들어주면 된다.
        val problemNum = 50
        val selectNum = 7
        val test = List(problemNum){ List(selectNum){0} }

        adapter = OmrInputAdapter(requireContext(), test)
        binding.omrInputList.adapter = adapter

        adapter?.submit(test)
    }

    private fun setOnClickListeners() = with(binding) {

    }

}