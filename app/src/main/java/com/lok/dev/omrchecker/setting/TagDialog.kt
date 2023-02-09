package com.lok.dev.omrchecker.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import com.lok.dev.commonbase.BaseDialogFragment
import com.lok.dev.commonutil.onUiState
import com.lok.dev.coredatabase.entity.TagTable
import com.lok.dev.commonBase.R
import com.lok.dev.commonbase.BaseBottomSheetDialogFragment
import com.lok.dev.omrchecker.databinding.DialogTagBinding
import com.lok.dev.omrchecker.setting.viewmodel.TagViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TagDialog : BaseBottomSheetDialogFragment<DialogTagBinding, Bundle>() {

    private val tagViewModel by viewModels<TagViewModel>()

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogTagBinding =
        DialogTagBinding.inflate(inflater, container, false)

    override fun initDialogFragment(savedInstanceState: Bundle?) {
        collectViewModel()
        addListeners()
        tagViewModel.getTagList()
    }

    private fun collectViewModel() = with(tagViewModel) {
        tagListData.onUiState(
            viewLifecycleOwner.lifecycleScope,
            error = {

            },
            success = {
                addChipInFlexbox(it)
            }
        )
    }

    private fun addListeners() {
        binding.btnAddTag.setOnClickListener {
            tagViewModel.addTag("testTag")
        }
    }

    private fun addChipInFlexbox(list: List<TagTable>) {
        list.forEach { tagData ->
            val chip = Chip(requireContext(), null, R.style.Theme_OMR_SubChip).apply {
                text = tagData.name
            }
            binding.layoutFlexbox.addView(chip)
        }
    }

}