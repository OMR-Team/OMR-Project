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

        scoreList.forEachIndexed { index, scoreInfo ->
            val modAnswerTable = answerList[index].copy(score = scoreInfo.score)
            answerList[index] = modAnswerTable
        }

        answerList.forEach{ answerTable ->
            addAnswerUseCase.invoke(answerTable)
        }
    }

}