package com.lok.dev.omrchecker.subject

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.lifecycleScope
import com.lok.dev.commonbase.BaseActivity
import com.lok.dev.commonbase.util.launchConfirmDialog
import com.lok.dev.commonmodel.state.AnimationState
import com.lok.dev.commonutil.*
import com.lok.dev.coredatabase.entity.OMRTable
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.databinding.ActivityOmrBinding
import com.lok.dev.omrchecker.dialog.TitleConfirmDialog
import com.lok.dev.omrchecker.subject.answer.AnswerInputFragment
import com.lok.dev.omrchecker.subject.omr.OmrInputFragment
import com.lok.dev.omrchecker.subject.result.ResultFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OmrActivity : BaseActivity<ActivityOmrBinding>() {

    companion object {
        const val PAGE_DEFAULT = 0
        const val PAGE_OMR_INPUT = 1
        const val PAGE_ANSWER_INPUT = 2
        const val PAGE_RESULT = 3
    }

    private val viewModel: OmrViewModel by viewModels()

    private lateinit var omrInputFragment: OmrInputFragment
    private lateinit var answerInputFragment: AnswerInputFragment
    private lateinit var resultFragment: ResultFragment

    override fun createBinding() = ActivityOmrBinding.inflate(layoutInflater)

    override fun initActivity(savedInstanceState: Bundle?) {
        super.initActivity(savedInstanceState)
        getExtra()
        collectViewModel()
        setClickListeners()
        setScreen()
    }

    private fun collectViewModel() = with(viewModel) {

        screenState.onResult(lifecycleScope) { screen ->
            changeScreen(screen)
        }

        progressState.onResult(lifecycleScope) { progress ->
            binding.progressProblemBar.updateLayoutParams {
                width = (progress.toDouble() / viewModel.tableData.problemNum * 100).dp
            }
        }

        answerProgressState.onResult(lifecycleScope) { progress ->
            binding.progressAnswerBar.updateLayoutParams {
                width = (progress.toDouble() / viewModel.tableData.problemNum * 100).dp
            }
        }

        tempOmrInputState.onUiState(lifecycleScope,
            success = { list ->
                viewModel.changeOmrInput(list)
            },
            error = {
                showToast(R.string.omr_error_temp_problem)
            }
        )

        tempAnswerInputState.onUiState(lifecycleScope,
            success = { list ->
                viewModel.changeAnswerInput(list)
            },
            error = {
                showToast(R.string.omr_error_temp_answer)
            }
        )
    }

    private fun getExtra() {
        intent.getParcelableExtra<OMRTable>("omrTable")?.let {
            viewModel.tableData = it
            viewModel.isTemp = it.isTemp
        } ?: showErrorDialog()
    }

    private fun showErrorDialog() {
        launchConfirmDialog(
            type = TitleConfirmDialog::class.java,
            option = {
                title = this.getString(R.string.omr_error_title)
                isCancelBtnVisible = false
            },
            result = {
                finish()
            }
        )
    }

    private fun setClickListeners() = with(binding) {

        throttleFirstClick(btnBack) {
            showCloseConfirmDialog()
        }

        throttleFirstClick(nextBtn) {
            when(getPresentFragmentName(R.id.omrFragment)) {
                OmrInputFragment::class.java.simpleName -> {
                    showProblemConfirmDialog()
                }
                AnswerInputFragment::class.java.simpleName -> {
                    showAnswerConfirmDialog()
                }
                else -> {
                    showProblemConfirmDialog()
                }
            }
        }

        throttleFirstClick(homeBtn) {
            finish()
        }


    }

    private fun showCloseConfirmDialog() {

        launchConfirmDialog(
            type = TitleConfirmDialog::class.java,
            option = {
                title = "임시 저장할까요?"
                subTitle = "취소를 누를 시 입력된 내용이 초기화됩니다."
            },
            result = {
                viewModel.saveInputData()
                val tempPage = if(getPresentFragmentName(R.id.omrFragment) == OmrInputFragment::class.java.simpleName) PAGE_OMR_INPUT
                else PAGE_ANSWER_INPUT
                viewModel.updateOMRTable(true, tempPage)
                finish()
            },
            cancel = {
                finish()
            }
        )

    }

    private fun showProblemConfirmDialog() {

        //TODO 안푼 문제 있을시에 따라 처리 다르게

        launchConfirmDialog(
            type = TitleConfirmDialog::class.java,
            option = {
                title = "정답 입력창으로 넘어가는 제목."
                subTitle = "테스트 다음으로 넘어가시겠습니까??"
            },
            result = {
                viewModel.saveInputData()
                viewModel.changeScreenState(OmrState.AnswerScreen)
            }
        )
    }

    private fun showAnswerConfirmDialog() {

        //TODO 점수 입력이 안되어있거나 정답 입력 안한부분 있을시에 처리 다르게

        launchConfirmDialog(
            type = TitleConfirmDialog::class.java,
            option = {
                title = "결과 창으로 넘어가는 제목."
                subTitle = "테스트 다음으로 넘어가시겠습니까??"
            },
            result = {
                viewModel.saveInputData()
                viewModel.updateOMRTable(false, PAGE_RESULT)
                viewModel.changeScreenState(OmrState.ResultScreen)
            }
        )
    }

    private fun changeScreen(state: OmrState) {
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
                binding.btnBack.visible(false)
                binding.nextBtn.visible(false)
                binding.homeBtn.visible(true)
                binding.resultCheck.setImageResource(R.drawable.resultcheck_complete)
            }
        }
    }

    private fun setScreen() = with(binding) {
        testName.text = viewModel.tableData.title
        subjectName.text = viewModel.tableData.subject.name
        val data = viewModel.tableData
        when {
            data.isTemp && data.page == PAGE_OMR_INPUT -> {
                viewModel.changeScreenState(OmrState.OmrScreen)
            }
            data.isTemp && data.page == PAGE_ANSWER_INPUT -> {
                viewModel.changeScreenState(OmrState.AnswerScreen)
            }
            !data.isTemp && data.page == PAGE_RESULT -> {
                viewModel.changeScreenState(OmrState.ResultScreen)
            }
            else -> {
                viewModel.changeScreenState(OmrState.OmrScreen)
            }
        }
    }

    sealed interface OmrState {
        object OmrScreen : OmrState
        object AnswerScreen : OmrState
        object ResultScreen : OmrState
    }
}


