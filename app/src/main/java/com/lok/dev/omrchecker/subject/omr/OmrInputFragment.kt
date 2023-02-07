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

    private var adapter: OmrInputAdapter? = null

    private val omrViewModel: OmrViewModel by activityViewModels()

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


    }

    private fun initAdapter() {

        adapter = OmrInputAdapter(requireContext()) { pair ->
            omrViewModel.updateProgress(pair)
        }
        binding.omrInputList.adapter = adapter
        adapter?.set(omrViewModel.omrInput.value)
    }

    private fun setOnClickListeners() = with(binding) {

    }

}