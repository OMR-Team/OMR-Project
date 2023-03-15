package com.lok.dev.omrchecker.home.fragment

import android.util.Log
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.lok.dev.commonbase.BaseFragment
import com.lok.dev.commonbase.util.launchDialogFragment
import com.lok.dev.commonmodel.state.FolderState
import com.lok.dev.commonutil.getWindowWidth
import com.lok.dev.commonutil.onResult
import com.lok.dev.commonutil.onUiState
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.custom.SortMenu
import com.lok.dev.omrchecker.custom.SortStandard
import com.lok.dev.omrchecker.databinding.FragmentFolderListBinding
import com.lok.dev.omrchecker.home.adapter.FolderListAdapter
import com.lok.dev.omrchecker.home.viewmodel.FolderListViewModel
import com.lok.dev.omrchecker.setting.SettingDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FolderListFragment @Inject constructor() : BaseFragment<FragmentFolderListBinding>() {

    private val viewModel: FolderListViewModel by viewModels()
    private val listAdapter by lazy {
        FolderListAdapter(
            viewLifecycleOwner.lifecycleScope,
            requireContext()
        )
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

        chipSort.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                showMenu(compoundButton)
            }
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
        binding.chipSort.text = when (standard) {
            SortStandard.NEWEST -> getString(R.string.sort_newest)
            SortStandard.ALPHABET -> getString(R.string.sort_alphabet)
            SortStandard.ADD -> getString(R.string.sort_add)
        }
        viewModel.sortSubjectList(standard)
    }

    private val onMenuDismiss = {
        binding.chipSort.isChecked = false
    }

    private fun addSettingFragment() {
        launchDialogFragment(
            dialogFragment = SettingDialog(),
            fullScreen = true
        )
    }

}

