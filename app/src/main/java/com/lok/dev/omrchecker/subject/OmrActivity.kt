package com.lok.dev.omrchecker.subject

import android.animation.Animator
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.updateLayoutParams
import com.lok.dev.commonbase.BaseActivity
import com.lok.dev.commonbase.util.launchConfirmDialog
import com.lok.dev.commonmodel.CommonConstants
import com.lok.dev.commonmodel.state.AnimationState
import com.lok.dev.commonutil.safeParcelable
import com.lok.dev.commonutil.showToast
import com.lok.dev.commonutil.throttleFirstClick
import com.lok.dev.commonutil.visible
import com.lok.dev.coredatabase.entity.OMRTable
import com.lok.dev.coredatabase.entity.SubjectTable
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
    private var backPressedCallback: OnBackPressedCallback? = null

    override fun createBinding() = ActivityOmrBinding.inflate(layoutInflater)

    override fun initActivity(savedInstanceState: Bundle?) {
        super.initActivity(savedInstanceState)
        getExtra()
        collectViewModel()
        setClickListeners()
        setScreen()
        backPressedCallback ?: setBackPressedCallback()
    }

    private fun collectViewModel() = with(viewModel) {

        screenState.onResult { screen ->
            changeScreen(screen)
        }

        progressState.onResult { progress ->
            binding.progressProblemBar.updateLayoutParams {
                width = (progress.toDouble() / viewModel.tableData.problemNum * 100).dp
            }
        }

        answerProgressState.onResult { progress ->
            binding.progressAnswerBar.updateLayoutParams {
                width = (progress.toDouble() / viewModel.tableData.problemNum * 100).dp
            }
        }

        tempOmrInputState.onUiState(
            success = { list ->
                viewModel.changeOmrInput(list)
            },
            error = {
                showToast(R.string.omr_error_temp_problem)
            }
        )

        saveResultData.onResult {
            viewModel.changeScreenState(OmrState.ResultScreen)
        }
    }

    private fun getExtra() {
        intent.safeParcelable<OMRTable>("omrTable")?.let {
            viewModel.tableData = it
            viewModel.isTemp = it.isTemp
        } ?: showErrorDialog()
        intent.safeParcelable<SubjectTable>(CommonConstants.BUNDLE_SUBJECT_DATA)?.let {
            viewModel.subjectData = it
        }
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
            when (getPresentFragmentName(R.id.omrFragment)) {
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
                title = this@OmrActivity.getString(R.string.omr_temp_save_title)
                subTitle = this@OmrActivity.getString(R.string.omr_temp_save_sub_title)
            },
            result = {
                viewModel.saveInputData()
                val tempPage =
                    if (getPresentFragmentName(R.id.omrFragment) == OmrInputFragment::class.java.simpleName) PAGE_OMR_INPUT
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
        if (viewModel.progressState.value != viewModel.tableData.problemNum) {
            launchConfirmDialog(
                type = TitleConfirmDialog::class.java,
                option = {
                    title = this@OmrActivity.getString(R.string.omr_progress_warn_title)
                    subTitle = this@OmrActivity.getString(R.string.omr_progress_warn_sub_title)
                },
                result = {
                    showAnswerScreen()
                }
            )
        } else {
            showAnswerScreen()
        }
    }

    private fun showAnswerScreen() {
        viewModel.saveInputData()
        viewModel.changeScreenState(OmrState.AnswerScreen)
    }

    private fun showAnswerConfirmDialog() {
        when {
            viewModel.answerProgressState.value != viewModel.tableData.problemNum && !viewModel.hasScore -> {
                launchConfirmDialog(
                    type = TitleConfirmDialog::class.java,
                    option = {
                        title = this@OmrActivity.getString(R.string.omr_answer_score_warn_title)
                        subTitle = this@OmrActivity.getString(R.string.omr_answer_warn_sub_title)
                    },
                    result = {
                        showResultScreen()
                    }
                )
            }
            viewModel.answerProgressState.value != viewModel.tableData.problemNum -> {
                launchConfirmDialog(
                    type = TitleConfirmDialog::class.java,
                    option = {
                        title = this@OmrActivity.getString(R.string.omr_answer_warn_title)
                        subTitle = this@OmrActivity.getString(R.string.omr_answer_warn_sub_title)
                    },
                    result = {
                        showResultScreen()
                    }
                )
            }
            !viewModel.hasScore -> {
                launchConfirmDialog(
                    type = TitleConfirmDialog::class.java,
                    option = {
                        title = this@OmrActivity.getString(R.string.omr_score_warn_title)
                        subTitle = this@OmrActivity.getString(R.string.omr_answer_warn_sub_title)
                    },
                    result = {
                        showResultScreen()
                    }
                )
            }
            else -> {
                showResultScreen()
            }
        }
    }

    private fun showResultScreen() {
        viewModel.saveInputData()
    }

    private fun changeScreen(state: OmrState) {
        when (state) {
            OmrState.OmrScreen -> {
                omrInputFragment = OmrInputFragment()
                replaceFragment(R.id.omrFragment, omrInputFragment, AnimationState.Left)
            }
            OmrState.AnswerScreen -> {
                answerInputFragment = AnswerInputFragment()
                replaceFragment(R.id.omrFragment, answerInputFragment, AnimationState.Left)
            }
            OmrState.ResultScreen -> {
                resultFragment = ResultFragment()
                replaceFragment(R.id.omrFragment, resultFragment, AnimationState.Left)
            }
        }
        changeScreenUI(state)
    }

    private fun changeScreenUI(state: OmrState) = with(binding) {
        when (state) {
            OmrState.OmrScreen -> {
                problemInput.visible()
                nextBtn.visible(true)
                homeBtn.visible(false)
            }
            OmrState.AnswerScreen -> {
                problemInput.visibility = View.INVISIBLE
                problemAni.visible()
                problemAni.addAnimatorListener(problemAnimatorListener)
                problemAni.playAnimation()
                answerInput.setImageResource(R.drawable.answerinput_complete)
                progressProblemBar.background =
                    AppCompatResources.getDrawable(this@OmrActivity, R.color.theme_blue_1)
                problemDesc.setTextColor(getColor(R.color.theme_black_3))
            }
            OmrState.ResultScreen -> {
                answerInput.visibility = View.INVISIBLE
                answerAni.visible()
                answerAni.addAnimatorListener(answerAnimatorListener)
                answerAni.playAnimation()
                btnBack.visible(false)
                nextBtn.visible(false)
                homeBtn.visible(true)
                resultCheck.setImageResource(R.drawable.resultcheck_complete)
                progressAnswerBar.background =
                    AppCompatResources.getDrawable(this@OmrActivity, R.color.theme_blue_1)
                answerDesc.setTextColor(getColor(R.color.theme_black_3))
            }
        }
    }

    private val problemAnimatorListener = object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {}
        override fun onAnimationCancel(animation: Animator) {}
        override fun onAnimationRepeat(animation: Animator) {}

        override fun onAnimationEnd(animation: Animator) {
            binding.problemAni.visible(false)
            binding.problemInput.visible()
            binding.problemInput.setImageResource(R.drawable.problem_input_past)
        }
    }

    private val answerAnimatorListener = object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {}
        override fun onAnimationCancel(animation: Animator) {}
        override fun onAnimationRepeat(animation: Animator) {}

        override fun onAnimationEnd(animation: Animator) {
            binding.answerAni.visible(false)
            binding.answerInput.visible()
            binding.answerInput.setImageResource(R.drawable.problem_input_past)
        }
    }

    private fun setScreen() = with(binding) {
        testName.text = viewModel.tableData.title
        subjectName.text = viewModel.subjectData?.name ?: ""
        val data = viewModel.tableData
        when {
            data.isTemp && data.page == PAGE_OMR_INPUT -> {
                viewModel.changeScreenState(OmrState.OmrScreen)
            }
            data.isTemp && data.page == PAGE_ANSWER_INPUT -> {
                viewModel.changeScreenState(OmrState.AnswerScreen)
                binding.progressProblemBar.updateLayoutParams { width = 100.dp }
            }
            !data.isTemp && data.page == PAGE_RESULT -> {
                viewModel.changeScreenState(OmrState.ResultScreen)
                omrProgressContainer.visible(false)
            }
            else -> {
                viewModel.changeScreenState(OmrState.OmrScreen)
            }
        }
    }

    private fun setBackPressedCallback() {
        backPressedCallback = this.onBackPressedDispatcher.addCallback(this) {
            if(viewModel.screen == OmrState.ResultScreen) finish()
            else showCloseConfirmDialog()
        }
    }

    sealed interface OmrState {
        object OmrScreen : OmrState
        object AnswerScreen : OmrState
        object ResultScreen : OmrState
    }
}


