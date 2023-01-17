package com.lok.dev.omrchecker.omrscreen.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import com.lok.dev.commonbase.BaseBottomSheetDialogFragment
import com.lok.dev.omrchecker.databinding.FragmentSettingBinding
import javax.inject.Inject

class SettingBottomSheetDialogFragment @Inject constructor() : BaseBottomSheetDialogFragment<FragmentSettingBinding>() {

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSettingBinding.inflate(layoutInflater)
}