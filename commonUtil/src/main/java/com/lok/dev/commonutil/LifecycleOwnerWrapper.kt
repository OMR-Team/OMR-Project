package com.lok.dev.commonutil

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.lok.dev.commonmodel.state.ResultUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

interface LifecycleOwnerWrapper {

    fun initLifeCycleOwner(): LifecycleOwner

    fun <T> Flow<ResultUiState<T>>.onUiState(
        loading: () -> Unit = {},
        success: (T) -> Unit = {},
        error: (Throwable) -> Unit = {},
        finish: () -> Unit = {}
    ) {
        onResult { state ->
            when (state) {
                ResultUiState.Loading -> loading()
                is ResultUiState.Success -> success(state.data)
                is ResultUiState.Error -> error(state.error)
                ResultUiState.Finish -> finish()
                else -> Unit
            }
        }
    }

    fun <T> Flow<T>.onResult(action: (T) -> Unit) {
        initLifeCycleOwner().lifecycleScope.launch {
            collect(action)
        }
    }

}