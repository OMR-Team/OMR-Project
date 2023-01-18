package com.lok.dev.omrchecker

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import com.lok.dev.commonbase.BaseActivity
import com.lok.dev.commonbase.launchDialog
import com.lok.dev.omrchecker.databinding.ActivityMainBinding
import com.lok.dev.omrchecker.subject.SubjectDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        const val OMR_INPUT_SCREEN = "omrInput"
        const val ANSWER_INPUT_SCREEN = "answerInput"
        const val RESULT_SCREEN = "resultScreen"

    }

    private val viewModel by viewModels<MainViewModel>()

    override fun createBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initActivity(savedInstanceState: Bundle?) {
        super.initActivity(savedInstanceState)


        // fragment 화면 전환 샘플
        binding.omrFragment.setOnClickListener {
            launchDialog(
                type = SubjectDialog::class.java,
                args = bundleOf("type" to OMR_INPUT_SCREEN)
            )
        }

    }


}