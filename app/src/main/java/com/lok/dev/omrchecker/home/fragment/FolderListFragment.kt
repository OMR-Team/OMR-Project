package com.lok.dev.omrchecker.home.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.lok.dev.commonbase.BaseFragment
import com.lok.dev.omrchecker.databinding.FragmentFolderListBinding

class FolderListFragment : BaseFragment<FragmentFolderListBinding>() {
    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFolderListBinding = FragmentFolderListBinding.inflate(inflater, container, false)

}