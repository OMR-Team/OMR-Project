package com.lok.dev.omrchecker.subject.omr

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.lok.dev.commonbase.BaseFragment
import com.lok.dev.commonutil.onResult
import com.lok.dev.omrchecker.databinding.FragmentOmrInputBinding
import com.lok.dev.omrchecker.subject.OmrViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class OmrInputFragment @Inject constructor() : BaseFragment<FragmentOmrInputBinding>() {

    private var adapter : OmrInputAdapter? = null

    private val omrViewModel : OmrViewModel by activityViewModels()

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentOmrInputBinding.inflate(inflater, container, false)

    override fun initFragment() {

        collectViewModel()
        initAdapter()
        setOnClickListeners()


    }

    private fun collectViewModel() {

        omrViewModel.omrInput.onResult(viewLifecycleOwner.lifecycleScope) {omrList ->
            adapter?.submit(omrList)
        }

    }

    private fun initAdapter() {

        adapter = OmrInputAdapter(requireContext(), omrViewModel.omrInputList) {
            val isChecked = it.first
            val problemNum = it.second[0]
            val selectNum = it.second[1]
            if(isChecked) {
                Log.d("123123123", "정답 체크함!! 문제번호 : ${problemNum}, 답안번호 : ${selectNum}")
                val tempList = omrViewModel.omrInputList.toMutableList()
                val modList = tempList[problemNum].toMutableList()
                modList[selectNum] = selectNum
                tempList[problemNum] = modList
                omrViewModel.changeOmrInput(tempList)
            }else {
                Log.d("123123123", "정답 체크해제함!! 문제번호 : ${problemNum}, 답안번호 : ${selectNum}")
                val tempList = omrViewModel.omrInputList.toMutableList()
                val modList = tempList[problemNum].toMutableList()
                modList[selectNum] = 0
                tempList[problemNum] = modList
                omrViewModel.changeOmrInput(tempList)
            }

        }
        binding.omrInputList.adapter = adapter
        adapter?.submit(omrViewModel.omrInputList)
    }

    private fun setOnClickListeners() = with(binding) {

    }

}