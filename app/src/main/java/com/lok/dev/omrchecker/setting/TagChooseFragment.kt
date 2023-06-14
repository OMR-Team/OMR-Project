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
import com.lok.dev.commonmodel.CommonConstants.CMD_ADD_TAG
import com.lok.dev.commonmodel.CommonConstants.TAG_NAME_IN_EDIT_TEXT
import com.lok.dev.commonmodel.state.TagState
import com.lok.dev.commonutil.*
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.databinding.FragmentTagChooseBinding
import com.lok.dev.omrchecker.home.adapter.TagChooseListAdapter
import com.lok.dev.omrchecker.setting.viewmodel.TagViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@AndroidEntryPoint
class TagChooseFragment(
    private val tagViewModel: TagViewModel
) : BaseFragment<FragmentTagChooseBinding>() {

    private val _checkedTagCnt = MutableStateFlow(0)
    val checkedTagCnt = _checkedTagCnt.asStateFlow()
    val checkedTagIdList = mutableListOf<Int>()

    private val tagAdapter by lazy {
        TagChooseListAdapter(requireContext(), viewLifecycleOwner.lifecycleScope, tagCheckedListener)
    }

    private val tagCheckedListener: (Boolean, Int) -> Unit = { isChecked: Boolean, tagId: Int ->
        if (isChecked) {
            _checkedTagCnt.value += 1
            checkedTagIdList.add(tagId)
        } else {
            _checkedTagCnt.value -= 1
            checkedTagIdList.remove(tagId)
        }
    }

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTagChooseBinding = FragmentTagChooseBinding.inflate(inflater, container, false)

    override fun initFragment() {
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
            error = {
                Log.e("아현", "$it")
            },
            success = {
                binding.tvEmpty.visible(it.isEmpty())
                tagAdapter.set(it)
            }
        )

        checkedTagCnt.onResult {
            if (it == 0) {
                binding.btnChoose.text = getString(R.string.text_choose)
                binding.btnChoose.isEnabled = false
            } else {
                binding.btnChoose.text = "${getString(R.string.text_choose)} ($it)"
                binding.btnChoose.isEnabled = true
            }
        }
    }

    private fun addListeners() = with(binding) {
        btnEdit.setOnClickListener {
            tagViewModel.setTagState(TagState.EDIT)
        }

        btnChoose.setOnClickListener {
            tagViewModel.setTagState(TagState.Finish(checkedTagIdList))
        }

        throttleFirstClick(btnAddTag) {
            launchDialogFragment(
                dialogFragment = TagEditTextDialog().apply {
                    arguments = bundleOf("cmd" to CMD_ADD_TAG)
                },
                result = {
                    val tagName = it?.getString(TAG_NAME_IN_EDIT_TEXT, "") ?: ""
                    if (tagName.trim().isNotEmpty()) tagViewModel.addTag(tagName)
                }
            )
        }
    }
}