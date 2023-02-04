package com.lok.dev.omrchecker.subject

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.lok.dev.commonbase.BaseActivity
import com.lok.dev.commonutil.onResult
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.databinding.ActivityOmrBinding
import com.lok.dev.omrchecker.subject.answer.AnswerInputFragment
import com.lok.dev.omrchecker.subject.omr.OmrInputFragment
import com.lok.dev.omrchecker.subject.result.ResultFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OmrActivity : BaseActivity<ActivityOmrBinding>() {

    private val viewModel : OmrViewModel by viewModels()

    private lateinit var omrInputFragment: OmrInputFragment
    private lateinit var answerInputFragment: AnswerInputFragment
    private lateinit var resultFragment: ResultFragment

    override fun createBinding() = ActivityOmrBinding.inflate(layoutInflater)

    override fun initActivity(savedInstanceState: Bundle?) {
        super.initActivity(savedInstanceState)

        collectViewModel()
        getExtra()

    }

    private fun collectViewModel() = with(viewModel) {

        screenState.onResult(lifecycleScope) { screen ->
            changeScreen(screen)
        }

    }

    private fun getExtra() {
        when (intent.extras?.get("type")) {
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

    private fun changeScreen(state: OmrState) {
        when (state) {
            OmrState.OmrScreen -> {
                omrInputFragment = OmrInputFragment()
                replaceFragment(R.id.omrFragment, omrInputFragment, Animation.Right)
            }
            OmrState.AnswerScreen -> {
                answerInputFragment = AnswerInputFragment()
                replaceFragment(R.id.omrFragment, answerInputFragment, Animation.Right)
            }
            OmrState.ResultScreen -> {
                resultFragment = ResultFragment()
                replaceFragment(R.id.omrFragment, resultFragment, Animation.Right)
            }
        }
    }

    sealed interface OmrState {
        object OmrScreen : OmrState
        object AnswerScreen : OmrState
        object ResultScreen : OmrState
    }
}


