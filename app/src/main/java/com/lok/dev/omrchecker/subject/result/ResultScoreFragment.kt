package com.lok.dev.omrchecker.subject.result

import android.view.LayoutInflater
import android.view.ViewGroup
import com.lok.dev.commonbase.BaseFragment
import com.lok.dev.omrchecker.databinding.FragmentResultScoreBinding

class ResultScoreFragment : BaseFragment<FragmentResultScoreBinding>() {

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentResultScoreBinding.inflate(inflater, container, false)

}