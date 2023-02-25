package com.lok.dev.omrchecker.test

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.lok.dev.commonbase.BaseFragment
import com.lok.dev.commonutil.getToday
import com.lok.dev.commonutil.onUiState
import com.lok.dev.coredatabase.entity.OMRTable
import com.lok.dev.coredatabase.entity.SubjectTable
import com.lok.dev.omrchecker.databinding.FragmentTestBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TestFragment @Inject constructor() : BaseFragment<FragmentTestBinding>() {

    private val viewModel by viewModels<TestViewModel>()

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTestBinding.inflate(inflater, container, false)

    override fun initFragment() {
        addListeners()
        collectViewModel()
        observeViewModel()
    }

    private fun addListeners() = with(binding) {
        btnInsert.setOnClickListener {
            val data = OMRTable(
                subject = SubjectTable(name = "테스트"),
                title = "테스트야",
                cnt = 0,
                problemNum = 10,
                selectNum = 5,
                tag = listOf(1, 2, 3),
                isTemp = true
            )
            viewModel.addTestInfo(data)
        }

        btnSelect.setOnClickListener {
            viewModel.getTestInfo()
        }
    }

    private fun collectViewModel() = with(viewModel) {
        testState.onUiState(lifecycleScope,
            loading = {
                Log.d("123123123", "test 로딩중!!!")
            },
            success = {
                Log.d("123123123", "test 성공!!!")
                Log.d("123123123", "결과 = $it")
            },
            error = {
                Log.d("123123123", "test 에러 = {$it}!!!")
            },
            finish = {
                Log.d("123123123", "test 끝!!!")
            }
        )
    }

    private fun observeViewModel() = with(viewModel) {

    }

}