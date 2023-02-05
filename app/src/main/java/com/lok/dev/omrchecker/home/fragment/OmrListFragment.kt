package com.lok.dev.omrchecker.home.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.lok.dev.commonbase.BaseFragment
import com.lok.dev.commonbase.util.launchDialogFragment
import com.lok.dev.omrchecker.databinding.FragmentOmrListBinding
import com.lok.dev.omrchecker.home.adapter.OmrListAdapter
import com.lok.dev.omrchecker.omrscreen.setting.SettingDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OmrListFragment @Inject constructor() : BaseFragment<FragmentOmrListBinding>() {

    private val omrListAdapter by lazy {
        OmrListAdapter(requireContext())
    }

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOmrListBinding = FragmentOmrListBinding.inflate(inflater, container, false)

    override fun initFragment() {
        addListeners()
        binding.rvList.adapter = omrListAdapter
    }

    private fun addListeners() = with(binding) {
        btnSetting.setOnClickListener {
            addSettingFragment()
        }
    }

    private fun addSettingFragment() {
        launchDialogFragment(
            dialogFragment = SettingDialog(),
            fullScreen = true
        )
    }

}