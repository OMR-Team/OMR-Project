package com.lok.dev.omrchecker.subject.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lok.dev.commonbase.BaseDialogFragment
import com.lok.dev.commonmodel.ResultJoinData
import com.lok.dev.commonutil.visible
import com.lok.dev.omrchecker.databinding.DialogResultAnswerBinding
import com.lok.dev.omrchecker.subject.result.adapter.ResultAnswerGridAdapter

class ResultAnswerDialog: BaseDialogFragment<DialogResultAnswerBinding, Bundle>(){

    lateinit var resultData: ResultJoinData

    private val inCorrectAdapter by lazy {
        ResultAnswerGridAdapter(requireContext())
    }

    private val correctAdapter by lazy {
        ResultAnswerGridAdapter(requireContext())
    }

    init {
        dimBehind = true
    }

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = DialogResultAnswerBinding.inflate(inflater, container, false)

    override fun initDialogFragment(savedInstanceState: Bundle?) {
        setAdapter()
        addListeners()
    }

    override fun initDataBinding() {
        binding.layoutIncorrect.visible(resultData.answer != resultData.problem)
    }

    private fun addListeners() = with(binding) {
        container.setOnClickListener {
            dismiss()
        }
    }

    private fun setAdapter() {
        binding.gridIncorrect.adapter = inCorrectAdapter
        inCorrectAdapter.set(resultData.problem)

        binding.gridCorrect.adapter = correctAdapter
        correctAdapter.set(resultData.answer)
    }


}