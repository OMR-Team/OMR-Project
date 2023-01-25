package com.lok.dev.omrchecker.subject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.lok.dev.commonbase.BaseDialogFragment
import com.lok.dev.omrchecker.MainActivity.Companion.ANSWER_INPUT_SCREEN
import com.lok.dev.omrchecker.MainActivity.Companion.OMR_INPUT_SCREEN
import com.lok.dev.omrchecker.MainActivity.Companion.RESULT_SCREEN
import com.lok.dev.omrchecker.databinding.DialogSubjectBinding
import com.lok.dev.omrchecker.navigator.FragmentNavigator
import com.lok.dev.omrchecker.navigator.OMRScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SubjectDialog @Inject constructor() : BaseDialogFragment<DialogSubjectBinding, Unit>() {

    init {
        bottomSlideAnimation = true
    }

    private val viewModel by viewModels<SubjectViewModel>()
    @Inject
    lateinit var fragmentNavigator: FragmentNavigator

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = DialogSubjectBinding.inflate(inflater, container, false)

    override fun initDialogFragment(savedInstanceState: Bundle?) {

        initScreen()

    }

    private fun initScreen() {
        val screen = when (arguments?.get("type")) {
            OMR_INPUT_SCREEN -> OMRScreen.OMRInput
            ANSWER_INPUT_SCREEN -> OMRScreen.AnswerInput
            RESULT_SCREEN -> OMRScreen.ResultView
            else -> OMRScreen.OMRInput
        }
        fragmentNavigator.naviOMRScreen(screen)
    }

    override fun onDialogBackPressed() {
        dismiss()
    }

}