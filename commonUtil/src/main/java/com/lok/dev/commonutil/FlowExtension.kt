package com.lok.dev.commonutil

import androidx.lifecycle.LifecycleCoroutineScope
import com.lok.dev.commonmodel.state.ResultUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/** Flow 내 데이터를 ResultState로 묶어 매핑 및 emit */
fun <T> Flow<T>.resultState(): Flow<ResultUiState<T>> {
    return this
        .map<T, ResultUiState<T>> {
            ResultUiState.Success(it)
        }
        .onStart { emit(ResultUiState.Loading) }
        .catch { emit(ResultUiState.Error(it)) }
        .onCompletion { emit(ResultUiState.Finish) }
}

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

inline fun <T> Flow<T>.collect(
    externalScope: CoroutineScope,
    crossinline collect: (T) -> Unit
) = onEach { collect.invoke(it) }.launchIn(externalScope)