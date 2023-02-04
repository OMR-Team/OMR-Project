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

        binding.testDataInsert.setOnClickListener {
            val data = OMRTable(
                subject = 100,
                title = "테스트야",
                problemNum = 10,
                selectNum = 5,
                tag = listOf(1,2,3)
            )
            viewModel.addTestInfo(data)
        }

        binding.settingFragment.setOnClickListener {
            launchDialogFragment(
                dialogFragment = settingDialog,
                fullScreen = true,
                fragmentManager = supportFragmentManager
            )
        }

        binding.getTestData.setOnClickListener {
            viewModel.getTestInfo()
        }

        viewModel.testState.onUiState(lifecycleScope,
            loading = {
                Log.d("123123123", "test 로딩중!!!")
            },
            success = {
                Log.d("123123123", "test 성공!!!")
                Log.d("123123123", "결과 = $it")
            },
            error = {
                Log.d("123123123", "test 에러 = {$it}!!!")
            },
            finish = {
                Log.d("123123123", "test 끝!!!")
            }
        )

    }


}