package com.lok.dev.omrchecker

import com.lok.dev.commonbase.BaseActivity
import com.lok.dev.omrchecker.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun createBinding() = ActivityMainBinding.inflate(layoutInflater)
}