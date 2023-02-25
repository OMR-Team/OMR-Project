package com.lok.dev.omrchecker.setting.subject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.lok.dev.commonbase.BaseDialogFragment
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

    init {
        dimBehind = true
    }

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
                    if(childFragmentManager.backStackEntryCount > 1) {
                        childFragmentManager.popBackStack()
                    } else {
                        addFragment(R.id.containerSubject, subjectListFragment, childFragmentManager, true)
                    }
                }
                SubjectState.Add -> {
                    addFragment(R.id.containerSubject, SubjectAddFragment(), childFragmentManager, true)
                }
                SubjectState.Edit -> {
                    addFragment(R.id.containerSubject, SubjectEditFragment(), childFragmentManager, true)
                }
                SubjectState.Exit -> dismiss()
                else -> {}
            }
        }
    }

    override fun onDialogBackPressed() {
        if(childFragmentManager.backStackEntryCount == 1) {
            dismiss()
        } else {
            childFragmentManager.popBackStack()
        }
    }
}