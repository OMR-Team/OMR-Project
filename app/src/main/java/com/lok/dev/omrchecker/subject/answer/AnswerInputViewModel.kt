package com.lok.dev.omrchecker.subject.answer

import androidx.lifecycle.viewModelScope
import com.lok.dev.commonbase.BaseViewModel
import com.lok.dev.commonmodel.state.mutableResultState
import com.lok.dev.commonutil.di.IoDispatcher
import com.lok.dev.commonutil.onState
import com.lok.dev.coredata.usecase.AddAnswerUseCase
import com.lok.dev.coredata.usecase.GetAnswerTableUseCase
import com.lok.dev.coredatabase.entity.AnswerTable
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
class AnswerInputViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val addAnswerUseCase: AddAnswerUseCase,
    private val getAnswerTableUseCase: GetAnswerTableUseCase,
) : BaseViewModel() {

    private val _answerList = MutableStateFlow<List<List<Pair<Int, Boolean>>>>(listOf())
    val answerList = _answerList.asStateFlow()

    val scoreList = mutableListOf<AnswerTable>()

    /** 임시저장 답안 리스트 불러오기 상태 **/
    private val _tempAnswerInputState = MutableSharedFlow<List<AnswerTable>>()
    val tempAnswerInputState = _tempAnswerInputState.asSharedFlow()

    /** 답안 리스트 **/
    private val _answerInput = MutableSharedFlow<List<AnswerTable>>()
    val answerInput = _answerInput.asSharedFlow()

    fun changeAnswerInput(list: List<AnswerTable>) {
        viewModelScope.launch {
            _answerInput.emit(list)
            scoreList.addAll(list)
        }
    }

    fun getAnswerTable(id: Int) = CoroutineScope(ioDispatcher).launch {
        _tempAnswerInputState.emit(getAnswerTableUseCase.invoke(id))
    }

    fun getAnswerList(answerList: List<AnswerTable>): List<AnswerTable> {
        val modAnswerList = answerList.toMutableList()
        scoreList.forEachIndexed { index, scoreInfo ->
            val modAnswerTable = answerList[index].copy(score = scoreInfo.score)
            modAnswerList[index] = modAnswerTable
        }
        return modAnswerList
    }

    fun addAnswerTable(answerList: List<AnswerTable>) = CoroutineScope(ioDispatcher).launch {
        answerList.forEach { answerTable ->
            addAnswerUseCase.invoke(answerTable)
        }
    }

    fun convertToAnswerList(list: List<AnswerTable>, selectNum: Int) {
        val tempList = List(list.size) { mutableListOf<Pair<Int, Boolean>>() }
        list.forEachIndexed { index, tableData ->
            for(i in 1..selectNum) {
                tempList[index].add(Pair(i, tableData.answer.contains(i)))
            }
        }
        _answerList.value = tempList
    }

    fun convertToAnswerTable(id: Int): List<AnswerTable> {
        val tableList = mutableListOf<AnswerTable>()
        answerList.value.forEachIndexed { index, answerList->
            val selectList = mutableListOf<Int>()
            answerList.forEach { answerPair ->
                if(answerPair.second) selectList.add(answerPair.first)
            }
            tableList.add(AnswerTable(id = id, no = index, answer = selectList, score = null))
        }
        return tableList
    }

    fun updateAnswerList(problemNum: Int, answerNum: Int, isSelected: Boolean) {
        val modProblemList = answerList.value.toMutableList()
        val modProblemTable = modProblemList[problemNum].toMutableList()
        modProblemTable[answerNum.minus(1)] = Pair(answerNum, isSelected)
        modProblemList[problemNum] = modProblemTable
        _answerList.value = modProblemList
    }

}