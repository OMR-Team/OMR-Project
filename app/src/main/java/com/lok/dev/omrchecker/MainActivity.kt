package com.lok.dev.omrchecker

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import com.lok.dev.commonbase.BaseActivity
import com.lok.dev.commonbase.util.launchDialogFragment
import com.lok.dev.omrchecker.databinding.ActivityMainBinding
import com.lok.dev.omrchecker.omrscreen.setting.SettingDialog
import com.lok.dev.omrchecker.subject.SubjectDialog
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

    @Inject lateinit var settingDialog: SettingDialog
    @Inject lateinit var subjectDialog: SubjectDialog

    override fun createBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initActivity(savedInstanceState: Bundle?) {
        super.initActivity(savedInstanceState)


        // fragment 화면 전환 샘플
        binding.omrFragment.setOnClickListener {
            launchDialogFragment(
                dialogFragment = subjectDialog.apply {
                    arguments = bundleOf("type" to OMR_INPUT_SCREEN)
                },
                fullScreen = true,
                fragmentManager = supportFragmentManager
            )
        }

        binding.settingFragment.setOnClickListener {
            launchDialogFragment(
                dialogFragment = settingDialog,
                fullScreen = true,
                fragmentManager = supportFragmentManager
            )
        }

    }


}