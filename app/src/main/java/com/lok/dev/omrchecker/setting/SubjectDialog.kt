package com.lok.dev.omrchecker.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.lok.dev.commonbase.BaseDialogFragment
import com.lok.dev.commonbase.util.launchDialogFragment
import com.lok.dev.commonmodel.state.SubjectState
import com.lok.dev.commonutil.addFragment
import com.lok.dev.commonutil.onResult
import com.lok.dev.commonutil.replaceFragment
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.databinding.DialogSubjectBinding
import com.lok.dev.omrchecker.setting.viewmodel.SettingViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SubjectDialog : BaseDialogFragment<DialogSubjectBinding, Bundle>() {

    private val viewModel by activityViewModels<SettingViewModel>()

    @Inject
    lateinit var subjectListFragment: SubjectListFragment

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogSubjectBinding =
        DialogSubjectBinding.inflate(layoutInflater, container, false)

    override fun initDialogFragment(savedInstanceState: Bundle?) {
        addListeners()
        collectViewModel()
        viewModel.setSubjectState(SubjectState.Select)
    }

    private fun addListeners() = with(binding) {
        container.backgroundTouchDismiss()
    }

    private fun collectViewModel() = with(viewModel) {
        subjectState.onResult(lifecycleScope) {
            when(it) {
                SubjectState.Select -> {
                    replaceFragment(R.id.containerSubject, subjectListFragment, childFragmentManager, true)
                }
                SubjectState.Add -> {
                    replaceFragment(R.id.containerSubject, SubjectAddFragment(), childFragmentManager, true)
                }
                else -> {}
            }
        }
    }
}