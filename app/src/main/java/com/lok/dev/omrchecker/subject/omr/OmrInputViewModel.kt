package com.lok.dev.omrchecker.subject.omr

import com.lok.dev.commonbase.BaseViewModel
import com.lok.dev.commonutil.di.IoDispatcher
import com.lok.dev.coredata.usecase.AddProblemUseCase
import com.lok.dev.coredatabase.entity.ProblemTable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OmrInputViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val addProblemUseCase: AddProblemUseCase
) : BaseViewModel() {

    private val _problemList = MutableStateFlow<List<List<Pair<Int, Boolean>>>>(listOf())
    val problemList = _problemList.asStateFlow()

    var id = 0
    var cnt = 0

    fun addProblemTable(problemList: List<ProblemTable>) = CoroutineScope(ioDispatcher).launch {

        problemList.forEach { problemTable ->
            addProblemUseCase.invoke(problemTable)
        }
    }

    fun convertToProblemList(list: List<ProblemTable>, selectNum : Int) {
        val tempList = List(list.size) { mutableListOf<Pair<Int, Boolean>>() }
        list.forEachIndexed { index, tableData ->
            for (i in 1..selectNum) {
                tempList[index].add(Pair(i, tableData.answer.contains(i)))
            }
        }
        _problemList.value = tempList
    }

    fun convertToProblemTable(id: Int, cnt: Int): List<ProblemTable> {
        val tableList = mutableListOf<ProblemTable>()
        problemList.value.forEachIndexed { index, problemList ->
            val answerList = mutableListOf<Int>()
            problemList.forEach { answerPair ->
                if (answerPair.second) answerList.add(answerPair.first)
            }
            tableList.add(ProblemTable(id = id, cnt = cnt, no = index, answer = answerList))
        }
        return tableList
    }

    fun updateProblemList(problemNum: Int, answerNum: Int, isSelected: Boolean) {
        val modProblemList = problemList.value.toMutableList()
        val modProblemTable = modProblemList[problemNum].toMutableList()
        modProblemTable[answerNum.minus(1)] = Pair(answerNum, isSelected)
        modProblemList[problemNum] = modProblemTable
        _problemList.value = modProblemList
    }
}