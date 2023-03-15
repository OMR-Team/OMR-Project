package com.lok.dev.omrchecker.home.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.lok.dev.commonbase.BaseFragment
import com.lok.dev.commonbase.util.launchDialogFragment
import com.lok.dev.commonutil.onUiState
import com.lok.dev.coredatabase.entity.OMRTable
import com.lok.dev.coredatabase.entity.SubjectTable
import com.lok.dev.omrchecker.databinding.FragmentOmrListBinding
import com.lok.dev.omrchecker.home.adapter.OmrListAdapter
import com.lok.dev.omrchecker.home.viewmodel.OmrListViewModel
import com.lok.dev.omrchecker.setting.SettingDialog
import com.lok.dev.omrchecker.setting.TagDialog
import com.lok.dev.omrchecker.subject.OmrActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OmrListFragment @Inject constructor() : BaseFragment<FragmentOmrListBinding>() {

    private val viewModel: OmrListViewModel by viewModels()
    private var subjectData: SubjectTable? = null

    private val omrListAdapter by lazy {
        OmrListAdapter(requireContext(), viewLifecycleOwner.lifecycleScope) { omrTable ->
            startOmrActivity(omrTable)
        }
    }

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOmrListBinding = FragmentOmrListBinding.inflate(inflater, container, false)

    override fun initFragment() {
        subjectData = arguments?.getParcelable("subject")
        initView()
        addListeners()
        collectViewModel()
        viewModel.getOmrList(subjectData)
    }

    private fun initView() = with(binding) {
        layoutHeader.textValue = subjectData?.name ?: ""
        rvList.adapter = omrListAdapter
    }

    private fun addListeners() = with(binding) {
        btnSetting.setOnClickListener {
            addSettingFragment()
        }

        chipTag.setOnClickListener {
            launchDialogFragment(
                dialogFragment = TagDialog(),
                bottomSlideAnimation = true
            )
        }

        chipSort.setOnCloseIconClickListener {
            Log.i("아현", "노노")
        }
    }

    private fun collectViewModel() = with(viewModel) {
        omrListData.onUiState(
            viewLifecycleOwner.lifecycleScope,
            error = {
                Log.e("아현", "$it")
            },
            success = {
                omrListAdapter.set(it)
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
        })
        this.startActivity(intent)
    }

}