package com.lok.dev.omrchecker.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.lok.dev.commonbase.BaseDialogFragment
import com.lok.dev.commonmodel.CommonConstants.TAG_STATE_REMOVE_TYPE_EDIT
import com.lok.dev.commonmodel.state.AnimationState
import com.lok.dev.commonmodel.state.TagState
import com.lok.dev.commonutil.addFragment
import com.lok.dev.commonutil.onResult
import com.lok.dev.commonutil.removeFragment
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.databinding.DialogTagBinding
import com.lok.dev.omrchecker.setting.viewmodel.TagViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TagDialog : BaseDialogFragment<DialogTagBinding, Bundle>() {

    private val tagViewModel by viewModels<TagViewModel>()
    private var tagEditFragment: TagEditFragment? = null

    init {
        windowHeight = WRAP_CONTENT
        dimBehind = true
    }

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogTagBinding = DialogTagBinding.inflate(inflater, container, false)

    override fun initDialogFragment(savedInstanceState: Bundle?) {
        collectViewModel()
        tagViewModel.setTagState(TagState.CHOOSE)
    }

    fun collectViewModel() = with(tagViewModel) {
        tagStateData.onResult(viewLifecycleOwner.lifecycleScope) {
            when (it) {
                TagState.CHOOSE -> {
                    addFragment(
                        R.id.containerTag,
                        TagChooseFragment(tagViewModel),
                        addBackStack = true
                    )
                }
                TagState.EDIT -> {
                    tagEditFragment = TagEditFragment(tagViewModel)
                    addFragment(R.id.containerTag, tagEditFragment, addBackStack = true)
                }
                is TagState.Remove -> {
                    if (it.type == TAG_STATE_REMOVE_TYPE_EDIT) {
                        removeFragment(tagEditFragment, animation = AnimationState.Right)
                    }
                }
                TagState.FINISH -> {
                    dismiss()
                }
            }
        }
    }
}