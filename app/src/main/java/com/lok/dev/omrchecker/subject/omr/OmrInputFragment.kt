package com.lok.dev.omrchecker.subject.omr

import android.view.LayoutInflater
import android.view.ViewGroup
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
        adapter = OmrInputAdapter(requireContext())


    }

    private fun setOnClickListeners() = with(binding) {



    }

}