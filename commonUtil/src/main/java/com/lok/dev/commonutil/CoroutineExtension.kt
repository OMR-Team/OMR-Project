package com.lok.dev.commonutil

import com.lok.dev.commonmodel.state.ResultUiState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun <T> CoroutineScope.resultCoroutine(
    apiCall: suspend ()->T,
    result: (ResultUiState<T>) -> Unit
) {
    launch(
        CoroutineExceptionHandler { _, throwable ->
            result.invoke(ResultUiState.Error(throwable))
        }
    ) {
        result.invoke(ResultUiState.Success(apiCall.invoke()))
    }.invokeOnCompletion {
        if (it == null) {
            result.invoke(ResultUiState.Finish)
        }
    }
}

fun <T> ResultUiState<T>.onEach(
    loading: () -> Unit = {},
    success: (T) -> Unit = {},
    error: (Throwable?) -> Unit = {},
    finish: () -> Unit = {}
) {
    when(this) {
        is ResultUiState.Loading -> {
            loading.invoke()
        }
        is ResultUiState.Success<T> -> {
            success(this.data)
        }
        is ResultUiState.Error -> {
            error.invoke(this.error)
        }
        is ResultUiState.Finish -> {
            finish.invoke()
        }
    }
}