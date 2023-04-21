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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnswerInputViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val addAnswerUseCase: AddAnswerUseCase,
    private val getAnswerTableUseCase: GetAnswerTableUseCase,
) : BaseViewModel() {

    val answerList = mutableListOf<AnswerTable>()
    val scoreList = mutableListOf<AnswerTable>()

    /** 임시저장 답안 리스트 불러오기 상태 **/
    private val _tempAnswerInputState = mutableResultState<List<AnswerTable>>()
    val tempAnswerInputState = _tempAnswerInputState.asStateFlow()

    fun getAnswerTable(id: Int) = CoroutineScope(ioDispatcher).launch {
        getAnswerTableUseCase.invoke(id).onState(viewModelScope) {
            _tempAnswerInputState.value = it
        }
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

    fun convertToAnswerList(list: List<AnswerTable>, selectNum: Int): List<MutableList<Pair<Int, Boolean>>> {
        val answerList = List(list.size) { mutableListOf<Pair<Int, Boolean>>() }
        list.forEachIndexed { index, tableData ->
            for(i in 1..selectNum) {
                answerList[index].add(Pair(i, tableData.answer.contains(i)))
            }
        }
        return answerList
    }

    fun convertToAnswerTable(list: List<List<Pair<Int, Boolean>>>?, id: Int): List<AnswerTable> {
        val tableList = mutableListOf<AnswerTable>()
        list?.forEachIndexed { index, answerList->
            val selectList = mutableListOf<Int>()
            answerList.forEach { answerPair ->
                if(answerPair.second) selectList.add(answerPair.first)
            }
            tableList.add(AnswerTable(id = id, no = index, answer = selectList, score = null))
        }
        return tableList
    }

}