package com.lok.dev.commonmodel.state

import kotlinx.coroutines.flow.MutableStateFlow

fun <T> mutableResultState(
    uiState: ResultUiState<T> = ResultUiState.UnInitialize
): MutableStateFlow<ResultUiState<T>> = MutableStateFlow(uiState)

sealed class ResultUiState<out T> {

    object UnInitialize : ResultUiState<Nothing>()

    /**
     * 결과 대기중
     * */
    object Loading : ResultUiState<Nothing>()

    /**
     * 결과 성공
     *  */
    data class Success<T>(val data: T) : ResultUiState<T>()

    /**
     * 결과 예외
     * */
    data class Error(val error: Throwable) : ResultUiState<Nothing>()

    /**
     * 결과 종료
     * */
    object Finish : ResultUiState<Nothing>()

}