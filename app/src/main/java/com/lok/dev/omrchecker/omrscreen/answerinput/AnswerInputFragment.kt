package com.lok.dev.omrchecker.omrscreen.answerinput

import android.view.LayoutInflater
import android.view.ViewGroup
import com.lok.dev.commonbase.BaseFragment
import com.lok.dev.omrchecker.databinding.FragmentAnswerInputBinding

class AnswerInputFragment : BaseFragment<FragmentAnswerInputBinding>() {

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAnswerInputBinding.inflate(inflater, container, false)

    override fun initFragment() {
        super.initFragment()
    }
}