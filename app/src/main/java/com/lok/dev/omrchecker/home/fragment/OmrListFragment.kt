package com.lok.dev.omrchecker.home.fragment

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.lok.dev.commonbase.BaseFragment
import com.lok.dev.commonbase.util.launchDialogFragment
import com.lok.dev.commonutil.onUiState
import com.lok.dev.omrchecker.databinding.FragmentOmrListBinding
import com.lok.dev.omrchecker.home.adapter.OmrListAdapter
import com.lok.dev.omrchecker.home.viewmodel.OmrListViewModel
import com.lok.dev.omrchecker.setting.SettingDialog
import com.lok.dev.omrchecker.setting.TagDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OmrListFragment @Inject constructor() : BaseFragment<FragmentOmrListBinding>() {

    private val viewModel: OmrListViewModel by viewModels()
    private val omrListAdapter by lazy {
        OmrListAdapter(requireContext())
    }

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOmrListBinding = FragmentOmrListBinding.inflate(inflater, container, false)

    override fun initFragment() {
        addListeners()
        collectViewModel()
        setRecyclerView()

        viewModel.getOmrList()
    }

    private fun addListeners() = with(binding) {
        btnSetting.setOnClickListener {
            addSettingFragment()
        }

        chipTag.setOnClickListener {
            launchDialogFragment(
                dialogFragment = TagDialog(),
                bottomSlideAnimation = true
            )
        }

        chipSort.setOnCloseIconClickListener {
            Log.i("아현", "노노")
        }
    }

    private fun setRecyclerView() {
        binding.rvList.adapter = omrListAdapter
    }

    private fun collectViewModel() = with(viewModel) {
        omrListData.onUiState(
            viewLifecycleOwner.lifecycleScope,
            error = {
                Log.e("아현", "$it")
            },
            success = {
                omrListAdapter.set(it)
            }
        )
    }

    private fun addSettingFragment() {
        launchDialogFragment(
            dialogFragment = SettingDialog(),
            fullScreen = true
        )
    }

}