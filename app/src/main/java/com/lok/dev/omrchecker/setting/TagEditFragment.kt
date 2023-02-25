package com.lok.dev.omrchecker.setting

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.lok.dev.commonbase.BaseFragment
import com.lok.dev.commonbase.util.launchDialogFragment
import com.lok.dev.commonmodel.CommonConstants.CMD_EDIT_TAG
import com.lok.dev.commonmodel.CommonConstants.TAG_NAME_IN_EDIT_TEXT
import com.lok.dev.commonmodel.CommonConstants.TAG_STATE_REMOVE_TYPE_EDIT
import com.lok.dev.commonmodel.state.TagState
import com.lok.dev.commonutil.*
import com.lok.dev.coredatabase.entity.TagTable
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.databinding.FragmentTagEditBinding
import com.lok.dev.omrchecker.home.adapter.TagEditListAdapter
import com.lok.dev.omrchecker.setting.viewmodel.TagViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TagEditFragment(
    private val tagViewModel: TagViewModel
) : BaseFragment<FragmentTagEditBinding>() {

    init {
        tagViewModel.setCheckedEditTagCnt(0)
    }

    private val checkedTagIdList = mutableListOf<Int>()

    private val tagAdapter by lazy {
        TagEditListAdapter(requireContext(), tagCheckedListener, tagLongClickListener)
    }

    private val tagCheckedListener: (Boolean, Int) -> Unit = { isChecked: Boolean, tagId: Int ->
        if (isChecked) {
            tagViewModel.setCheckedEditTagCnt(tagViewModel.checkedEditTagCnt.value + 1)
            checkedTagIdList.add(tagId)
        } else {
            tagViewModel.setCheckedEditTagCnt(tagViewModel.checkedEditTagCnt.value - 1)
            checkedTagIdList.remove(tagId)
        }
    }

    private val tagLongClickListener: (Int) -> Unit = { tagId: Int ->
        launchDialogFragment(
            TagEditTextDialog().apply {
                arguments = bundleOf("cmd" to CMD_EDIT_TAG)
            },
            result = {
                val changedTagName = it?.getString(TAG_NAME_IN_EDIT_TEXT, "") ?: ""
                if (changedTagName.trim().isNotEmpty()) {
                    tagViewModel.updateTag(TagTable(tagId, changedTagName))
                }
            }
        )
    }

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTagEditBinding = FragmentTagEditBinding.inflate(inflater, container, false)

    override fun initFragment() {
        initFlexBoxLayout()
        collectViewModel()
        addListeners()
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
                tagAdapter.set(it)
            }
        )

        checkedEditTagCnt.onResult(viewLifecycleOwner.lifecycleScope) {
            if (it == 0) {
                binding.btnDelete.text = getString(R.string.text_delete)
                binding.btnDelete.isEnabled = false
            } else {
                binding.btnDelete.text = "${getString(R.string.text_delete)} ($it)"
                binding.btnDelete.isEnabled = true
            }
        }
    }

    private fun addListeners() = with(binding) {
        btnReset.setOnClickListener {
            tagViewModel.setCheckedEditTagCnt(0)
        }

        throttleFirstClick(btnDelete) {
            tagViewModel.deleteTag(checkedTagIdList)
        }

        btnFinish.setOnClickListener {
            tagViewModel.setTagState(TagState.Remove(TAG_STATE_REMOVE_TYPE_EDIT))
        }
    }

}