package com.lok.dev.omrchecker.subject

import androidx.lifecycle.viewModelScope
import com.lok.dev.commonbase.BaseViewModel
import com.lok.dev.commonmodel.state.mutableResultState
import com.lok.dev.commonutil.di.IoDispatcher
import com.lok.dev.commonutil.onState
import com.lok.dev.coredata.usecase.AddProblemUseCase
import com.lok.dev.coredata.usecase.GetAnswerTableUseCase
import com.lok.dev.coredata.usecase.GetProblemTableUseCase
import com.lok.dev.coredata.usecase.UpdateOmrUseCase
import com.lok.dev.coredatabase.entity.AnswerTable
import com.lok.dev.coredatabase.entity.OMRTable
import com.lok.dev.coredatabase.entity.ProblemTable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OmrViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val updateOmrUseCase: UpdateOmrUseCase,
    private val getProblemTableUseCase: GetProblemTableUseCase,
    private val getAnswerTableUseCase: GetAnswerTableUseCase
) : BaseViewModel() {

    private val problemSelected = mutableMapOf<Int, Int>()
    private val answerSelected = mutableMapOf<Int, Int>()

    lateinit var tableData : OMRTable
    var isTemp = false

    /** 문제 입력 진행도 **/
    private val _progressState = MutableStateFlow(0)
    val progressState = _progressState.asStateFlow()

    /** 답안 입력 진행도 **/
    private val _answerProgressState = MutableStateFlow(0)
    val answerProgressState = _answerProgressState.asStateFlow()

    /** 현재 보이는 화면 상태 **/
    private val _screenState = MutableStateFlow<OmrActivity.OmrState>(OmrActivity.OmrState.OmrScreen)
    val screenState = _screenState.asStateFlow()

    /** 임시저장 문제 리스트 불러오기 상태 **/
    private val _tempOmrInputState = mutableResultState<List<ProblemTable>>()
    val tempOmrInputState = _tempOmrInputState.asStateFlow()

    /** 임시저장 답안 리스트 불러오기 상태 **/
    private val _tempAnswerInputState = mutableResultState<List<AnswerTable>>()
    val tempAnswerInputState = _tempAnswerInputState.asStateFlow()

    /** 문제 리스트 **/
    private val _omrInput = MutableStateFlow<List<ProblemTable>>(listOf())
    val omrInput = _omrInput.asStateFlow()

    /** 답안 리스트 **/
    private val _answerInput = MutableStateFlow<List<AnswerTable>>(listOf())
    val answerInput = _answerInput.asStateFlow()

    /** 문제 / 정답 목록 저장 **/
    private val _saveInputData = MutableSharedFlow<Unit>()
    val saveInputData = _saveInputData.asSharedFlow()

    fun changeScreenState(state: OmrActivity.OmrState) {
        _screenState.value = state
    }

    fun changeOmrInput(list: List<ProblemTable>) {
        _omrInput.value = list
    }

    fun changeAnswerInput(list: List<AnswerTable>) {
        _answerInput.value = list
    }

    fun saveInputData() {
        viewModelScope.launch {
            _saveInputData.emit(Unit)
        }
    }

    fun updateProgress(pair: Pair<Boolean, Int>) {
        val isChecked = pair.first
        val problemNum = pair.second
        when {
            isChecked -> problemSelected[problemNum] = problemSelected.getOrDefault(problemNum, 0).plus(1)
            problemSelected.getOrDefault(problemNum, 0) == 1 -> problemSelected.remove(problemNum)
            else -> problemSelected[problemNum] = problemSelected.getOrDefault(problemNum, 0).minus(1)
        }
        _progressState.value = problemSelected.size
    }

    fun updateAnswerProgress(pair: Pair<Boolean, Int>) {
        val isChecked = pair.first
        val problemNum = pair.second
        when {
            isChecked -> answerSelected[problemNum] = answerSelected.getOrDefault(problemNum, 0).plus(1)
            answerSelected.getOrDefault(problemNum, 0) == 1 -> answerSelected.remove(problemNum)
            else -> answerSelected[problemNum] = answerSelected.getOrDefault(problemNum, 0).minus(1)
        }
        _answerProgressState.value = answerSelected.size
    }

    fun updateOMRTable(isTemp : Boolean) = CoroutineScope(ioDispatcher).launch {
        updateOmrUseCase.invoke(tableData.copy(isTemp = isTemp, updateDate = System.currentTimeMillis()))
    }

    fun getProblemTable() = CoroutineScope(ioDispatcher).launch {
        getProblemTableUseCase.invoke(tableData.id).onState(viewModelScope) {
            _tempOmrInputState.value = it
        }
    }
    fun makeProblemTable() {
        val problemList = arrayListOf<ProblemTable>()
        val answerList = List(tableData.selectNum){ 0 }
        for (i in 0 until tableData.problemNum) {
            problemList.add(ProblemTable(tableData.id, tableData.cnt, i.plus(1), answerList))
        }
        _omrInput.value = problemList
    }

    fun getAnswerTable() = CoroutineScope(ioDispatcher).launch {
        getAnswerTableUseCase.invoke(tableData.id).onState(viewModelScope) {
            _tempAnswerInputState.value = it
        }
    }
}