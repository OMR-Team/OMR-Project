 package com.lok.dev.omrchecker.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lok.dev.commonbase.BaseBottomSheetDialogFragment
import com.lok.dev.omrchecker.databinding.DialogSubjectBinding

class SubjectDialog : BaseBottomSheetDialogFragment<DialogSubjectBinding, Bundle>() {

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogSubjectBinding =
        DialogSubjectBinding.inflate(layoutInflater, container, false)

}