package com.lok.dev.omrchecker.subject.omr

import com.lok.dev.commonbase.BaseViewModel
import com.lok.dev.commonutil.di.IoDispatcher
import com.lok.dev.coredata.usecase.AddProblemUseCase
import com.lok.dev.coredatabase.entity.ProblemTable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OmrInputViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val addProblemUseCase: AddProblemUseCase
) : BaseViewModel() {

    var id = 0
    var cnt = 0

    fun addProblemTable(problemList: List<ProblemTable>) = CoroutineScope(ioDispatcher).launch {

        problemList.forEach { problemTable ->
            addProblemUseCase.invoke(problemTable)
        }
    }

    fun convertToProblemList(list: List<ProblemTable>, selectNum : Int): List<MutableList<Pair<Int, Boolean>>> {
        val problemList = List(list.size) { mutableListOf<Pair<Int, Boolean>>() }
        list.forEachIndexed { index, tableData ->
            for (i in 1..selectNum) {
                problemList[index].add(Pair(i, tableData.answer.contains(i)))
            }
        }
        return problemList
    }

    fun convertToProblemTable(list: List<List<Pair<Int, Boolean>>>?, id: Int, cnt: Int): List<ProblemTable> {
        val tableList = mutableListOf<ProblemTable>()
        list?.forEachIndexed { index, problemList ->
            val answerList = mutableListOf<Int>()
            problemList.forEach { answerPair ->
                if (answerPair.second) answerList.add(answerPair.first)
            }
            tableList.add(ProblemTable(id = id, cnt = cnt, no = index, answer = answerList))
        }
        return tableList
    }
}