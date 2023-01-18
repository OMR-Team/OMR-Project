package com.lok.dev.omrchecker.navigator

import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.lok.dev.commonbase.BaseFragment
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.subject.answer.AnswerInputFragment
import com.lok.dev.omrchecker.subject.omr.OMRInputFragment
import javax.inject.Inject


class FragmentNavigatorImpl @Inject constructor(
    private val fragmentActivity: FragmentActivity
) : FragmentNavigator {

    override fun naviMainScreen(screen: MainScreen) {

    }

    override fun naviOMRScreen(screen: OMRScreen) {
        val fragment = when(screen) {
            OMRScreen.OMRInput -> OMRInputFragment()
            OMRScreen.AnswerInput -> AnswerInputFragment()
            else -> OMRInputFragment()
        }
        replaceFragment(fragment)
    }

    private fun replaceFragment(fragment : BaseFragment<out ViewDataBinding>) {
        fragmentActivity.supportFragmentManager.popBackStack(
            fragment::class.java.canonicalName,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        fragmentActivity.supportFragmentManager.beginTransaction()
            .replace(R.id.subjectFrame, fragment)
            .addToBackStack(fragment::class.java.canonicalName)
            .commit()
    }
}