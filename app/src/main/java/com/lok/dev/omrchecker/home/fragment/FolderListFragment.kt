package com.lok.dev.omrchecker.home.fragment

import android.R.attr.button
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.lok.dev.commonbase.BaseFragment
import com.lok.dev.commonbase.util.launchConfirmDialog
import com.lok.dev.commonbase.util.launchDialogFragment
import com.lok.dev.commonmodel.state.FolderState
import com.lok.dev.commonutil.*
import com.lok.dev.coredatabase.entity.SubjectTable
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.custom.SortMenu
import com.lok.dev.omrchecker.custom.SortStandard
import com.lok.dev.omrchecker.databinding.FragmentFolderListBinding
import com.lok.dev.omrchecker.dialog.TitleConfirmDialog
import com.lok.dev.omrchecker.home.adapter.FolderListAdapter
import com.lok.dev.omrchecker.home.viewmodel.FolderListViewModel
import com.lok.dev.omrchecker.setting.SettingDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class FolderListFragment @Inject constructor() : BaseFragment<FragmentFolderListBinding>() {

    private val viewModel: FolderListViewModel by viewModels()
    private val listAdapter by lazy {
        FolderListAdapter(
            viewLifecycleOwner.lifecycleScope,
            requireContext(),
            folderLongClick,
            folderClick
        )
    }

    val containerBtnGap by lazy { 56.dp / 10 }
    val containerSettingGap by lazy { 48.dp / 10 }
    private val folderLongClick: (SubjectTable) -> Unit = {
        lifecycleScope.launch {
            repeat(10) {
                delay(10)
                binding.containerBtn.updateLayoutParams<MarginLayoutParams> {
                    bottomMargin += containerBtnGap
                }

                binding.containerSetting.updateLayoutParams<MarginLayoutParams> {
                    topMargin -= containerSettingGap
                }
            }
        }
    }

    private val folderClick: (SubjectTable) -> Unit = {
        requireActivity().addFragment(R.id.fragment, OmrListFragment().apply {
            arguments = Bundle().apply {
                putParcelable("subject", it)
            }
        })
    }

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFolderListBinding = FragmentFolderListBinding.inflate(inflater, container, false)

    override fun initFragment() {
        initView()
        addListeners()
        collectViewModel()
        viewModel.getFolderList()
    }

    private fun initView() = with(binding) {
    }

    private fun addListeners() = with(binding) {
        btnFolder.setOnClickListener {
            when (it.tag) {
                FolderState.GRID_2.ordinal -> {
                    viewModel.updateFolderState(FolderState.LINEAR.ordinal)
                }
                FolderState.LINEAR.ordinal -> {
                    viewModel.updateFolderState(FolderState.GRID_2.ordinal)
                }
            }
        }

        btnSetting.setOnClickListener {
            addSettingFragment()
        }

        spinnerSort.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                showMenu(compoundButton)
            }
        }

        throttleFirstClick(btnDelete) {
            showDeleteDialog()
        }

    }

    private fun collectViewModel() = with(viewModel) {
        folderState.onResult(viewLifecycleOwner.lifecycleScope) { ordinal ->
            binding.btnFolder.apply {
                tag = ordinal
                setImageResource(if (ordinal == FolderState.LINEAR.ordinal) R.drawable.ico_folder_grid_2 else R.drawable.ico_folder_list)
            }
            val layoutManager = when (ordinal) {
                FolderState.GRID_2.ordinal -> {
                    val columnCnt = requireActivity().getWindowWidth() / 160.dp
                    GridLayoutManager(requireContext(), columnCnt)
                }
                FolderState.LINEAR.ordinal -> {
                    LinearLayoutManager(requireContext())
                }
                else -> null
            }
            layoutManager?.let {
                binding.rvFolder.layoutManager = it
                binding.rvFolder.adapter = listAdapter
                listAdapter.updateFolderState(ordinal)
            }
        }

        subjectListData.onUiState(
            viewLifecycleOwner.lifecycleScope,
            error = {
                Log.i(this::class.java.simpleName, it.printStackTrace().toString())
            },
            success = {
                listAdapter.set(it)
            }
        )
    }

    private fun showMenu(view: View) {
        SortMenu(requireContext(), view, onClickMenu, onMenuDismiss).show()
    }

    private val onClickMenu: (SortStandard) -> Unit = { standard ->
        binding.spinnerSort.text = when (standard) {
            SortStandard.NEWEST -> getString(R.string.sort_newest)
            SortStandard.ALPHABET -> getString(R.string.sort_alphabet)
            SortStandard.ADD -> getString(R.string.sort_add)
        }
        viewModel.sortSubjectList(standard)
    }

    private val onMenuDismiss = {
        binding.spinnerSort.isChecked = false
    }

    private fun addSettingFragment() {
        launchDialogFragment(
            dialogFragment = SettingDialog(),
            fullScreen = true
        )
    }

    private fun showDeleteDialog() {
        launchConfirmDialog(
            type = TitleConfirmDialog::class.java,
            option = {
                title = this@FolderListFragment.getString(R.string.folder_list_delete_title)
                subTitle = this@FolderListFragment.getString(R.string.folder_list_delete_sub_title)
                cancelText = R.string.text_cancel
                confirmText = R.string.text_delete
            },
            result = {
                // TODO 폴더 삭제 로직 구현 필요
            }
        )
    }

}

