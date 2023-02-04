package com.lok.dev.omrchecker

import android.os.Bundle
import androidx.activity.viewModels
import com.lok.dev.commonbase.BaseActivity
import com.lok.dev.omrchecker.databinding.ActivityMainBinding
import com.lok.dev.omrchecker.omrlist.OmrListFragment
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

    @Inject
    lateinit var omrListFragment: OmrListFragment

    override fun createBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initActivity(savedInstanceState: Bundle?) {
        supportFragmentManager.beginTransaction().add(R.id.fragment, omrListFragment).commit()
    }

}