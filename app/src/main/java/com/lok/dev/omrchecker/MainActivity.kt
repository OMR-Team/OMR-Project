package com.lok.dev.omrchecker

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.lok.dev.commonbase.BaseActivity
import com.lok.dev.omrchecker.databinding.ActivityMainBinding
import com.lok.dev.omrchecker.navigator.FragmentNavigator
import com.lok.dev.omrchecker.navigator.OMRScreen
import com.lok.dev.omrchecker.omrscreen.omrinput.OMRInputFragment
import com.lok.dev.omrchecker.omrscreen.setting.SettingBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    @Inject lateinit var fragmentNavigator : FragmentNavigator
    @Inject lateinit var settingFragment: SettingBottomSheetDialogFragment

    override fun createBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initActivity(savedInstanceState: Bundle?) {
        super.initActivity(savedInstanceState)


        // fragment 화면 전환 샘플
        binding.omrFragment.setOnClickListener {
            fragmentNavigator.naviOMRScreen(OMRScreen.OMRInput)
        }

        binding.answerFragment.setOnClickListener {
            fragmentNavigator.naviOMRScreen(OMRScreen.AnswerInput)
        }

        binding.settingFragment.setOnClickListener {
            settingFragment.show(supportFragmentManager, settingFragment.tag)
        }

    }


}