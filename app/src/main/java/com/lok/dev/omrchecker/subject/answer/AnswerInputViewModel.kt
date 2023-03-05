package com.lok.dev.omrchecker.subject.answer

import com.lok.dev.commonbase.BaseViewModel
import com.lok.dev.commonutil.di.IoDispatcher
import com.lok.dev.coredata.usecase.AddAnswerUseCase
import com.lok.dev.coredatabase.entity.AnswerTable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnswerInputViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val addAnswerUseCase: AddAnswerUseCase
) : BaseViewModel() {

    val answerList = mutableListOf<AnswerTable>()
    val scoreList = mutableListOf<AnswerTable>()

    fun addAnswerTable(answerList: MutableList<AnswerTable>) = CoroutineScope(ioDispatcher).launch {

        // TODO 수정!!!!!!
        val tempList = answerList.toMutableList()

        scoreList.forEachIndexed { index, scoreInfo ->
            val modAnswerTable = tempList[index].copy(score = scoreInfo.score)
            tempList[index] = modAnswerTable
        }

        tempList.forEach{ answerTable ->
            addAnswerUseCase.invoke(answerTable)
        }
    }

}