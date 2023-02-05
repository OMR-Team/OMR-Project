package com.lok.dev.omrchecker

import android.os.Bundle
import androidx.activity.viewModels
import com.lok.dev.commonbase.BaseActivity
import com.lok.dev.omrchecker.databinding.ActivityMainBinding
import com.lok.dev.omrchecker.test.TestFragment
import com.lok.dev.omrchecker.home.fragment.OmrListFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel by viewModels<MainViewModel>()

    @Inject lateinit var omrListFragment: OmrListFragment
    @Inject lateinit var testFragment: TestFragment

    override fun createBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initActivity(savedInstanceState: Bundle?) {
        // OmrActivity 띄우기
//        val intent = Intent(this, OmrActivity::class.java)
//        intent.putExtras(bundleOf("type" to "omr"))
//        startActivity(intent)

        addOmrListFragment()
        //addTestFragment()
    }

    private fun addOmrListFragment() {
        supportFragmentManager.beginTransaction().add(R.id.fragment, omrListFragment).commit()
    }

    private fun addTestFragment() {
        supportFragmentManager.beginTransaction().add(R.id.fragment, testFragment).commit()
    }

}