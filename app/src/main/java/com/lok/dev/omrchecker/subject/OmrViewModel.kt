package com.lok.dev.omrchecker.subject

import androidx.lifecycle.viewModelScope
import com.lok.dev.commonbase.BaseViewModel
import com.lok.dev.commonmodel.state.mutableResultState
import com.lok.dev.commonutil.di.IoDispatcher
import com.lok.dev.commonutil.onState
import com.lok.dev.coredata.usecase.*
import com.lok.dev.coredatabase.entity.*
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
    private val updateSubjectUseCase: UpdateSubjectUseCase,
    private val getProblemTableUseCase: GetProblemTableUseCase,
    private val addHistoryUseCase: AddHistoryUseCase,
    private val addOmrUseCase: AddOmrUseCase
) : BaseViewModel() {

    private val problemSelected = mutableMapOf<Int, Int>()
    private val answerSelected = mutableMapOf<Int, Int>()

    lateinit var tableData : OMRTable
    var answerTable = listOf<AnswerTable>()
    var problemTable = listOf<ProblemTable>()
    var subjectData: SubjectTable? = null
    var isTemp = false
    var hasScore = false
    var screen: OmrActivity.OmrState = OmrActivity.OmrState.AnswerScreen

    /** 문제 입력 진행도 **/
    private val _progressState = MutableStateFlow(0)
    val progressState = _progressState.asStateFlow()

    /** 답안 입력 진행도 **/
    private val _answerProgressState = MutableStateFlow(0)
    val answerProgressState = _answerProgressState.asStateFlow()

    /** 현재 보이는 화면 상태 **/
    private val _screenState = MutableSharedFlow<OmrActivity.OmrState>()
    val screenState = _screenState.asSharedFlow()

    /** 임시저장 문제 리스트 불러오기 상태 **/
    private val _tempOmrInputState = mutableResultState<List<ProblemTable>>()
    val tempOmrInputState = _tempOmrInputState.asStateFlow()

    /** 문제 리스트 **/
    private val _omrInput = MutableSharedFlow<List<ProblemTable>>()
    val omrInput = _omrInput.asSharedFlow()

    /** 문제 / 정답 목록 저장 **/
    private val _saveInputData = MutableSharedFlow<Unit>()
    val saveInputData = _saveInputData.asSharedFlow()

    /** 결과 저장 상태 **/
    private val _saveResultData = MutableSharedFlow<Long>()
    val saveResultData = _saveResultData.asSharedFlow()

    fun changeScreenState(state: OmrActivity.OmrState) {
        viewModelScope.launch {
            _screenState.emit(state)
            screen = state
        }
    }

    fun changeOmrInput(list: List<ProblemTable>) {
        viewModelScope.launch {
            _omrInput.emit(list)
        }
    }

    fun saveInputData() {
        viewModelScope.launch {
            _saveInputData.emit(Unit)
        }
    }

    fun updateProblemProgress(problemNum: Int, isSelected: Boolean) {
        when {
            isSelected -> problemSelected[problemNum] = problemSelected.getOrDefault(problemNum, 0).plus(1)
            problemSelected.getOrDefault(problemNum, 0) == 1 -> problemSelected.remove(problemNum)
            else -> problemSelected[problemNum] = problemSelected.getOrDefault(problemNum, 0).minus(1)
        }
        _progressState.value = problemSelected.size
    }

    fun updateAnswerProgress(problemNum: Int, isSelected: Boolean) {
        when {
            isSelected -> answerSelected[problemNum] = answerSelected.getOrDefault(problemNum, 0).plus(1)
            answerSelected.getOrDefault(problemNum, 0) == 1 -> answerSelected.remove(problemNum)
            else -> answerSelected[problemNum] = answerSelected.getOrDefault(problemNum, 0).minus(1)
        }
        _answerProgressState.value = answerSelected.size
    }

    fun updateOMRTable(isTemp : Boolean, page: Int) = CoroutineScope(ioDispatcher).launch {
        val currentTime = System.currentTimeMillis()
        updateOmrUseCase.invoke(tableData.copy(isTemp = isTemp, page = page, updateDate = currentTime))
        updateSubjectUseCase.invoke(currentTime, tableData.subjectId)
        if(!isTemp) addHistoryTable()
    }

    private fun addHistoryTable() = CoroutineScope(ioDispatcher).launch {
        var correct = 0
        var totalScore = 0.0
        val score = problemTable.sumOf { problemTable ->
            answerTable.firstOrNull { answerTable -> problemTable.no == answerTable.no  }?.let { answer ->
                totalScore += answer.score ?: 0.0
                if(problemTable.answer.isNotEmpty() && answer.answer.isNotEmpty() && problemTable.answer == answer.answer) {
                    correct++
                    answer.score ?: 0.0
                }
                else 0.0
            } ?: 0.0
        }
        val addState = addHistoryUseCase.invoke(HistoryTable(tableData.id, tableData.cnt, score, totalScore, correct))
        _saveResultData.emit(addState)
    }

    fun getProblemTable() = CoroutineScope(ioDispatcher).launch {
        getProblemTableUseCase.invoke(tableData.id).onState(viewModelScope) {
            _tempOmrInputState.value = it
        }
    }
    fun makeProblemTable() {
        val problemList = mutableListOf<ProblemTable>()
        val answerList = mutableListOf<Int>()
        for (i in 1 .. tableData.problemNum) {
            problemList.add(ProblemTable(tableData.id, tableData.cnt, i, answerList))
        }
        viewModelScope.launch {
            _omrInput.emit(problemList)
        }
    }

    fun addOmrTest(callBack: (OMRTable) -> Unit) = CoroutineScope(ioDispatcher).launch {
        val omrTable = OMRTable(
            id = tableData.id,
            cnt = tableData.cnt + 1,
            subjectId = tableData.subjectId,
            title = tableData.title,
            problemNum = tableData.problemNum,
            selectNum = tableData.selectNum
        )
        addOmrUseCase.invoke(omrTable)
        callBack.invoke(omrTable)
    }
}