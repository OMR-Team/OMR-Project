package com.lok.dev.omrchecker

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import com.lok.dev.commonbase.BaseActivity
import com.lok.dev.omrchecker.databinding.ActivityMainBinding
import com.lok.dev.omrchecker.home.fragment.FolderListFragment
import com.lok.dev.omrchecker.home.fragment.OmrListFragment
import com.lok.dev.omrchecker.subject.OmrActivity
import com.lok.dev.omrchecker.subject.result.ResultFragment
import com.lok.dev.omrchecker.test.TestFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel by viewModels<MainViewModel>()

    override fun createBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initActivity(savedInstanceState: Bundle?) {
        addFolderListFragment()
        //addOmrListFragment()
        //startOmrActivity()
        //addTestFragment()
        //addResultFragment()
    }

    private fun addFolderListFragment() {
        addFragment(R.id.fragment, FolderListFragment())
    }

    private fun addOmrListFragment() {
        addFragment(R.id.fragment, OmrListFragment())
    }

    private fun addTestFragment() {
        addFragment(R.id.fragment, TestFragment())
    }

    private fun addResultFragment() {
        addFragment(R.id.fragment, ResultFragment())
    }

    private fun startOmrActivity() {
        val intent = Intent(this, OmrActivity::class.java)
        intent.putExtras(bundleOf("type" to "omr"))
        startActivity(intent)
    }
}