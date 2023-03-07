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


    fun addProblemTable(problemList: MutableList<ProblemTable>) = CoroutineScope(ioDispatcher).launch {

        problemList.forEach { problemTable ->
            addProblemUseCase.invoke(problemTable)
        }
    }
}