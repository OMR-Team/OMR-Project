package com.lok.dev.commonmodel.state

import kotlinx.coroutines.flow.MutableStateFlow

fun <T> mutableResultState(
    uiState: ResultUiState<T> = ResultUiState.Loading
): MutableStateFlow<ResultUiState<T>> = MutableStateFlow(uiState)

sealed class ResultUiState<out T> {
    object Loading : ResultUiState<Nothing>()
    data class Success<T>(val data: T) : ResultUiState<T>()
    data class Error(val error: Throwable) : ResultUiState<Nothing>()
    object Finish : ResultUiState<Nothing>()
}