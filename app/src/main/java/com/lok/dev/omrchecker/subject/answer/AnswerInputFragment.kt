package com.lok.dev.omrchecker.subject.answer

import android.view.LayoutInflater
import android.view.ViewGroup
import com.lok.dev.commonbase.BaseFragment
import com.lok.dev.omrchecker.databinding.FragmentAnswerInputBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AnswerInputFragment @Inject constructor() : BaseFragment<FragmentAnswerInputBinding>() {

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAnswerInputBinding.inflate(inflater, container, false)

    override fun initFragment() {

        setOnClickListeners()

    }

    private fun setOnClickListeners() = with(binding) {



    }
}