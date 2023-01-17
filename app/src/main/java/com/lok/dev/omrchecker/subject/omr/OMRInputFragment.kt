package com.lok.dev.omrchecker.subject.omr

import android.view.LayoutInflater
import android.view.ViewGroup
import com.lok.dev.commonbase.BaseFragment
import com.lok.dev.omrchecker.databinding.FragmentOmrInputBinding
import com.lok.dev.omrchecker.navigator.FragmentNavigator
import com.lok.dev.omrchecker.navigator.OMRScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OMRInputFragment @Inject constructor() : BaseFragment<FragmentOmrInputBinding>() {

    private var adapter : OMRInputAdapter? = null
    @Inject
    lateinit var fragmentNavigator: FragmentNavigator

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentOmrInputBinding.inflate(inflater, container, false)

    override fun initFragment() {

        initAdapter()
        setOnClickListeners()


    }

    private fun initAdapter() {
        adapter = OMRInputAdapter(requireContext())


    }

    private fun setOnClickListeners() = with(binding) {

        nextBtn.setOnClickListener {
            fragmentNavigator.naviOMRScreen(OMRScreen.AnswerInput)
        }


    }

}