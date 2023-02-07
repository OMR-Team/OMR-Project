package com.lok.dev.omrchecker.subject

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import com.lok.dev.commonbase.BaseActivity
import com.lok.dev.commonutil.collect
import com.lok.dev.commonutil.onResult
import com.lok.dev.commonutil.throttleFirst
import com.lok.dev.coredatabase.entity.ProblemTable
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


        // 나중에 getExtra() 에서 임시저장인지 아닌지를 먼저 보고 fragment 를 열어줘야함
        // 임시저장일 때와 아닐떄로 분기처리
        // 임시저장일때는 임시저장했던 리스트를 가져와서 array 를 만들어줘야하고
        // 임시저장이 아닐때는 그냥 만들어주면 된다.
        val test = arrayListOf<ProblemTable>()
        for(i in 0 until 50) {
            test.add(ProblemTable(i, 0, i + 1, listOf(0, 0, 0, 0, 0)))
        }
        viewModel.changeOmrInput(test)


        getExtra()
        setClickListeners()


    }

    private fun collectViewModel() = with(viewModel) {

        screenState.onResult(lifecycleScope) { screen ->
            changeScreen(screen)
        }

        progressState.onResult(lifecycleScope) {
            val progress = it.size
            binding.progressBar.startAnimation(makeAnimation(binding.progressBar, 0.3F, 500))
        }

    }

    private fun makeAnimation(
        view: View,
        targetPercent: Float,
        time: Long
    ): Animation {
        return object : Animation() {
            override fun applyTransformation(
                interpolatedTime: Float,
                t: Transformation?
            ) {
                val params = view.layoutParams as ConstraintLayout.LayoutParams
                val oldPercent = params.matchConstraintPercentWidth
                params.matchConstraintPercentWidth = oldPercent + ((targetPercent - oldPercent) * interpolatedTime)
                view.layoutParams = params
            }
        }.apply {
            duration = time
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

    private fun setClickListeners() = with(binding) {


        nextBtn.throttleFirst(1500).collect(lifecycleScope) {
            // 누르면 확인 창 띄우고 omrInputAdapter 의 adapter list 를 db 에 저장
            // sharedFlow 를 viewModel 에 만들어두고 omrFragment 에서 그걸 구독하는 식??
            Log.d("123123123", "다음!")
        }

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
            }
            OmrState.ResultScreen -> {
                resultFragment = ResultFragment()
                replaceFragment(R.id.omrFragment, resultFragment, AnimationState.Right)
            }
        }
    }

    sealed interface OmrState {
        object OmrScreen : OmrState
        object AnswerScreen : OmrState
        object ResultScreen : OmrState
    }
}


