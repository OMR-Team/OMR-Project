package com.lok.dev.omrchecker.subject

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.lifecycleScope
import com.lok.dev.commonbase.BaseActivity
import com.lok.dev.commonbase.util.launchConfirmDialog
import com.lok.dev.commonbase.util.launchDialogFragment
import com.lok.dev.commonutil.collect
import com.lok.dev.commonutil.onResult
import com.lok.dev.commonutil.px
import com.lok.dev.commonutil.throttleFirst
import com.lok.dev.coredatabase.entity.ProblemTable
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.databinding.ActivityOmrBinding
import com.lok.dev.omrchecker.dialog.TitleConfirmDialog
import com.lok.dev.omrchecker.setting.SettingDialog.Companion.OMR_ANSWER_NUM
import com.lok.dev.omrchecker.setting.SettingDialog.Companion.OMR_PROBLEM_NUM
import com.lok.dev.omrchecker.setting.SettingDialog.Companion.OMR_SUBJECT_NAME
import com.lok.dev.omrchecker.setting.SettingDialog.Companion.OMR_TEST_NAME
import com.lok.dev.omrchecker.subject.answer.AnswerInputFragment
import com.lok.dev.omrchecker.subject.omr.OmrInputFragment
import com.lok.dev.omrchecker.subject.result.ResultFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OmrActivity : BaseActivity<ActivityOmrBinding>() {

    private val viewModel: OmrViewModel by viewModels()

    private lateinit var omrInputFragment: OmrInputFragment
    private lateinit var answerInputFragment: AnswerInputFragment
    private lateinit var resultFragment: ResultFragment

    override fun createBinding() = ActivityOmrBinding.inflate(layoutInflater)

    override fun initActivity(savedInstanceState: Bundle?) {
        super.initActivity(savedInstanceState)

        collectViewModel()
        getExtra()
        setClickListeners()
        setBody()


        // 나중에 getExtra() 에서 임시저장인지 아닌지를 먼저 보고 fragment 를 열어줘야함
        // 임시저장일 때와 아닐떄로 분기처리
        // 임시저장일때는 임시저장했던 리스트를 가져와서 array 를 만들어줘야하고
        // 임시저장이 아닐때는 그냥 만들어주면 된다.
        val test = arrayListOf<ProblemTable>()
        for (i in 0 until viewModel.problemNum) {
            test.add(ProblemTable(i, 0, i + 1, listOf(0, 0, 0, 0, 0)))
        }
        viewModel.changeOmrInput(test)

    }

    private fun collectViewModel() = with(viewModel) {

        screenState.onResult(lifecycleScope) { screen ->
            changeScreen(screen)
        }

        progressState.onResult(lifecycleScope) { progress ->
            binding.progressProblemBar.updateLayoutParams {
                width = (progress.toDouble() / viewModel.problemNum * 100).px(applicationContext)
            }
        }

        answerProgressState.onResult(lifecycleScope) { progress ->
            binding.progressAnswerBar.updateLayoutParams {
                width = (progress.toDouble() / viewModel.answerNum * 100).px(applicationContext)
            }

        }
    }

    private fun getExtra() {
        viewModel.subjectName = intent.getStringExtra(OMR_SUBJECT_NAME).orEmpty()
        viewModel.testName = intent.getStringExtra(OMR_TEST_NAME).orEmpty()
        viewModel.problemNum = intent.getIntExtra(OMR_PROBLEM_NUM, 0)
        viewModel.answerNum = intent.getIntExtra(OMR_ANSWER_NUM, 0)

        when (intent.getStringExtra("type")) {
            "omr" -> {
                viewModel.changeScreenState(OmrState.OmrScreen)
            }
            "answer" -> {
                viewModel.changeScreenState(OmrState.AnswerScreen)
            }
            "result" -> {
                viewModel.changeScreenState(OmrState.ResultScreen)
            }
        }
    }

    private fun setClickListeners() = with(binding) {

        nextBtn.throttleFirst(1500).collect(lifecycleScope) {
            // 누르면 확인 창 띄우고 omrInputAdapter 의 adapter list 를 db 에 저장
            // sharedFlow 를 viewModel 에 만들어두고 omrFragment 에서 그걸 구독하는 식??

            // TODO 화면 전환 로직 추가 수정 필요

            launchConfirmDialog(
                type = TitleConfirmDialog::class.java,
                option = {
                    title = "이것은 제목 테스트 입니다."
                    subTitle = "테스트 다음으로 넘어가시겠습니까??"
                },
                result = {
                    if(viewModel.screenState.value is OmrState.OmrScreen) viewModel.changeScreenState(OmrState.AnswerScreen)
                    else viewModel.changeScreenState(OmrState.ResultScreen)
                }
            )

        }

    }

    private fun changeScreen(state: OmrState) {
        //TODO 로티 재생시켜줄떄 밑에 있는 뷰 gone 말고 invisible 써서 레이아웃 모양 유지하기

        when (state) {
            OmrState.OmrScreen -> {
                omrInputFragment = OmrInputFragment()
                replaceFragment(R.id.omrFragment, omrInputFragment, AnimationState.Right)
            }
            OmrState.AnswerScreen -> {
                answerInputFragment = AnswerInputFragment()
                replaceFragment(R.id.omrFragment, answerInputFragment, AnimationState.Right)
                binding.problemInput.visibility = View.INVISIBLE
                binding.problemAni.visibility = View.VISIBLE
                binding.problemAni.playAnimation()
                binding.answerInput.setImageResource(R.drawable.answerinput_complete)
            }
            OmrState.ResultScreen -> {
                resultFragment = ResultFragment()
                replaceFragment(R.id.omrFragment, resultFragment, AnimationState.Right)
                binding.answerInput.visibility = View.INVISIBLE
                binding.answerAni.visibility = View.VISIBLE
                binding.answerAni.playAnimation()
                binding.resultCheck.setImageResource(R.drawable.resultcheck_complete)
            }
        }
    }

    private fun setBody() = with(binding) {
        testName.text = viewModel.testName
        subjectName.text = viewModel.subjectName
    }

    sealed interface OmrState {
        object OmrScreen : OmrState
        object AnswerScreen : OmrState
        object ResultScreen : OmrState
    }
}


