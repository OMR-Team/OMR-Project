package com.lok.dev.omrchecker

import android.os.Bundle
import android.os.Debug
import android.util.Log
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.lok.dev.commonbase.BaseActivity
import com.lok.dev.commonbase.util.launchDialogFragment
import com.lok.dev.commonutil.onUiState
import com.lok.dev.coredatabase.entity.OMRTable
import com.lok.dev.omrchecker.databinding.ActivityMainBinding
import com.lok.dev.omrchecker.omrlist.OmrListFragment
import com.lok.dev.omrchecker.test.TestFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        const val OMR_INPUT_SCREEN = "omrInput"
        const val ANSWER_INPUT_SCREEN = "answerInput"
        const val RESULT_SCREEN = "resultScreen"
    }

    private val viewModel by viewModels<MainViewModel>()

    @Inject lateinit var omrListFragment: OmrListFragment
    @Inject lateinit var testFragment: TestFragment

    override fun createBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initActivity(savedInstanceState: Bundle?) {
        addOmrListFragment()
        //addTestFragment()
    }

    private fun addOmrListFragment() {
        supportFragmentManager.beginTransaction().add(R.id.fragment, omrListFragment).commit()
    }

    private fun addTestFragment() {
        supportFragmentManager.beginTransaction().add(R.id.fragment, testFragment).commit()
    }

}