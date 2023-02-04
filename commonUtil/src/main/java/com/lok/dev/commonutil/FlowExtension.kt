package com.lok.dev.commonutil

import androidx.lifecycle.LifecycleCoroutineScope
import com.lok.dev.commonmodel.state.ResultUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

fun <T> Flow<T>.onState(
    scope: CoroutineScope,
    startDelay: Long? = null,
    collect: (ResultUiState<T>) -> Unit,
) {
    onStartAndEndDelay(startDelay) { collect(ResultUiState.Loading) }
        .mapLatest { collect(ResultUiState.Success(it)) }
        .onEach { collect(ResultUiState.Finish) }
        .catch { collect(ResultUiState.Error(it)) }
        .launchIn(scope)
}

fun <T> Flow<T>.onStartAndEndDelay(
    delay: Long? = null,
    action: suspend () -> Unit
): Flow<T> = onStart {
    action.invoke()
    if (delay != null) delay(delay)
}

fun <T> Flow<ResultUiState<T>>.onUiState(
    scope: LifecycleCoroutineScope,
    loading: () -> Unit = {},
    success: (T) -> Unit = {},
    error: (Throwable) -> Unit = {},
    finish: () -> Unit = {}
) {
    onResult(scope) { state ->
        when (state) {
            ResultUiState.Loading -> loading()
            is ResultUiState.Success -> success(state.data)
            is ResultUiState.Error -> error(state.error)
            ResultUiState.Finish -> finish()
            else -> Unit
        }
    }
}

fun <T> Flow<T>.onResult(scope: LifecycleCoroutineScope, action: (T) -> Unit) {
    scope.launch {
        collect(action)
    }
}