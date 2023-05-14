package com.lok.dev.omrchecker.home.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.lok.dev.commonbase.BaseFragment
import com.lok.dev.commonbase.util.launchDialogFragment
import com.lok.dev.commonmodel.CommonConstants
import com.lok.dev.commonutil.*
import com.lok.dev.coredatabase.entity.OMRTable
import com.lok.dev.coredatabase.entity.SubjectTable
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.custom.SortStandard
import com.lok.dev.omrchecker.databinding.FragmentOmrListBinding
import com.lok.dev.omrchecker.home.adapter.OmrListAdapter
import com.lok.dev.omrchecker.home.adapter.OmrOngoingListAdapter
import com.lok.dev.omrchecker.home.viewmodel.OmrListViewModel
import com.lok.dev.omrchecker.setting.SettingDialog
import com.lok.dev.omrchecker.setting.TagDialog
import com.lok.dev.omrchecker.subject.OmrActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OmrListFragment : BaseFragment<FragmentOmrListBinding>() {

    private val tag = this::class.java.simpleName

    override val enableBackPressed = true
    private val viewModel: OmrListViewModel by viewModels()
    private val subjectData by lazy {
        arguments.safeParcelable<SubjectTable>("subject")
    }

    private val omrOngoingListAdapter by lazy {
        OmrOngoingListAdapter(viewLifecycleOwner.lifecycleScope, requireContext()) { omrTable ->
            startOmrActivity(omrTable)
        }
    }

    private val omrListAdapter by lazy {
        OmrListAdapter(viewLifecycleOwner.lifecycleScope, requireContext()) { omrTable ->
            startOmrActivity(omrTable)
        }
    }

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOmrListBinding = FragmentOmrListBinding.inflate(inflater, container, false)

    override fun initFragment() {
        initView()
        addListeners()
        collectViewModel()
        initApiCall()
    }

    private fun initView() = with(binding) {
        binding.motionLayout.progress = 1f
        layoutHeader.textValue = subjectData?.name ?: ""
        rvOngoing.adapter = omrOngoingListAdapter
        rvList.adapter = omrListAdapter
        rvList.layoutManager = LayoutManagerWrapper(requireContext())
        settingSpinner()
    }

    private fun settingSpinner() {
        val items = listOf(
            getString(R.string.sort_newest),
            getString(R.string.sort_alphabet),
            getString(R.string.sort_add)
        )
        val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.item_spinner_sort, items)
        binding.spinnerSort.apply {
            adapter = spinnerAdapter
            setSelection(0)
            dropDownVerticalOffset = 7f.dp
            dropDownHorizontalOffset = 7f.dp
            dropDownWidth = 100f.dp
        }
    }

    private fun initApiCall() {
        viewModel.getOmrList(subjectData?.id ?: 0)
        viewModel.getOmrOngoingList(subjectData?.id ?: 0)
    }

    private fun addListeners() = with(binding) {
        layoutHeader.btnBack.setOnClickListener {
            onFragmentBackPressed()
        }

        btnSetting.setOnClickListener {
            addSettingFragment()
        }

        chipTag.setOnClickListener {
            launchDialogFragment(
                dialogFragment = TagDialog(),
                bottomSlideAnimation = true
            )
        }

        spinnerSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val sortType = when(position) {
                    0 -> SortStandard.NEWEST
                    1 -> SortStandard.ALPHABET
                    else -> SortStandard.ADD
                }
                viewModel.sortSubjectList(sortType)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun collectViewModel() = with(viewModel) {
        isOngoingListEmpty.onResult(lifecycleScope) { isListEmpty ->
            when (isListEmpty) {
                true -> {
                    binding.motionLayout.transitionToEnd()
                    binding.motionLayout.enableTransition(R.id.transitionDragUp, false)
                }
                false -> {
                    binding.motionLayout.enableTransition(R.id.transitionDragUp, true)
                    binding.motionLayout.transitionToStart()
                }
                else -> {}
            }
        }

        omrOngoingListData.onUiState(
            viewLifecycleOwner.lifecycleScope,
            error = { Log.e(tag, "omrOngoingListData: $it") },
            success = {
                omrOngoingListAdapter.updateList(it)
            }
        )

        omrDoneListData.onUiState(
            viewLifecycleOwner.lifecycleScope,
            error = { Log.e(tag, "omrListData: $it") },
            success = {
                omrListAdapter.updateList(it)
            }
        )
    }

    private fun addSettingFragment() {
        launchDialogFragment(
            dialogFragment = SettingDialog(),
            fullScreen = true
        )
    }

    private fun startOmrActivity(omrTable: OMRTable) {
        val intent = Intent(requireContext(), OmrActivity::class.java)
        intent.putExtras(Bundle().apply {
            putParcelable("omrTable", omrTable)
            putParcelable(CommonConstants.BUNDLE_SUBJECT_DATA, subjectData)
        })
        this.startActivity(intent)
    }

    override fun onFragmentBackPressed() {
        requireActivity().removeFragment(this@OmrListFragment)
    }

}