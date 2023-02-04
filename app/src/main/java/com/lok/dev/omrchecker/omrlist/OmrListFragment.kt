package com.lok.dev.omrchecker.omrlist

import android.view.LayoutInflater
import android.view.ViewGroup
import com.lok.dev.commonbase.BaseFragment
import com.lok.dev.commonbase.util.launchDialogFragment
import com.lok.dev.omrchecker.databinding.FragmentOmrListBinding
import com.lok.dev.omrchecker.omrscreen.setting.SettingDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OmrListFragment @Inject constructor() : BaseFragment<FragmentOmrListBinding>() {

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOmrListBinding
        = FragmentOmrListBinding.inflate(inflater, container, false)

    override fun initFragment() {
        addListeners()
    }

    private fun addListeners() = with(binding){
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