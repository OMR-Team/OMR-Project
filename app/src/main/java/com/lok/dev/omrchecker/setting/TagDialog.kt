package com.lok.dev.omrchecker.setting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.lok.dev.commonbase.BaseDialogFragment
import com.lok.dev.commonbase.util.launchDialogFragment
import com.lok.dev.commonutil.onUiState
import com.lok.dev.commonutil.visible
import com.lok.dev.omrchecker.databinding.DialogTagBinding
import com.lok.dev.omrchecker.home.adapter.TagListAdapter
import com.lok.dev.omrchecker.setting.viewmodel.TagViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TagDialog : BaseDialogFragment<DialogTagBinding, Bundle>() {

    private val tagViewModel by viewModels<TagViewModel>()
    private val tagAdapter by lazy { TagListAdapter(requireContext()) }
    override var windowHeight = WRAP_CONTENT

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogTagBinding =
        DialogTagBinding.inflate(inflater, container, false)

    override fun initDialogFragment(savedInstanceState: Bundle?) {
        initFlexBoxLayout()
        collectViewModel()
        addListeners()
        tagViewModel.getTagList()
    }

    private fun initFlexBoxLayout() = with(binding) {
        FlexboxLayoutManager(requireContext()).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
        }.let {
            layoutFlexbox.layoutManager = it
            layoutFlexbox.adapter = tagAdapter
        }
    }

    private fun collectViewModel() = with(tagViewModel) {
        tagListData.onUiState(
            viewLifecycleOwner.lifecycleScope,
            error = {
                Log.e("아현", "$it")
            },
            success = {
                binding.tvEmpty.visible(it.isEmpty())
                tagAdapter.set(it)
            }
        )
    }

    private fun addListeners() = with(binding) {
        btnAddTag.setOnClickListener {
            launchDialogFragment(
                dialogFragment = TagEditDialog().apply {
                    arguments = bundleOf("cmd" to "AddTag")
                },
                result = {
                    val tagName = it?.getString("editText", "") ?: ""
                    if (tagName.isNotEmpty()) tagViewModel.addTag(tagName)
                }
            )
        }
    }

}