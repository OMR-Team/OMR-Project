package com.lok.dev.omrchecker.subject.result

import androidx.lifecycle.viewModelScope
import com.lok.dev.commonbase.BaseViewModel
import com.lok.dev.commonmodel.ResultJoinData
import com.lok.dev.commonmodel.state.mutableResultState
import com.lok.dev.commonutil.di.IoDispatcher
import com.lok.dev.commonutil.onState
import com.lok.dev.coredata.usecase.GetHistoryUseCase
import com.lok.dev.coredata.usecase.GetResultJoinUseCase
import com.lok.dev.coredatabase.entity.AnswerTable
import com.lok.dev.coredatabase.entity.HistoryTable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val resultJoinUseCase: GetResultJoinUseCase,
    private val getHistoryUseCase: GetHistoryUseCase
) : BaseViewModel() {

    /** 현재회차 결과 리스트 **/
    private val _resultJoinState = mutableResultState<List<ResultJoinData>>()
    val resultJoinState = _resultJoinState.asStateFlow()

    private val _historyState = mutableResultState<HistoryTable>()
    val historyState = _historyState.asStateFlow()

    fun getResultJoin(id: Int, no: Int) = CoroutineScope(ioDispatcher).launch {
        resultJoinUseCase.invoke(id, no).onState(viewModelScope) {
            _resultJoinState.value = it
        }
    }

    fun getHistoryData(id: Int, cnt: Int) = CoroutineScope(ioDispatcher).launch {
        getHistoryUseCase.invoke(id, cnt).onState(viewModelScope) {
            _historyState.value = it
        }
    }
}