package com.lok.dev.omrchecker.setting.viewmodel

import androidx.lifecycle.viewModelScope
import com.lok.dev.commonbase.BaseViewModel
import com.lok.dev.commonmodel.state.SubjectState
import com.lok.dev.commonmodel.state.mutableResultState
import com.lok.dev.commonutil.di.IoDispatcher
import com.lok.dev.commonutil.onState
import com.lok.dev.coredata.usecase.AddSubjectUseCase
import com.lok.dev.coredata.usecase.GetSubjectListUseCase
import com.lok.dev.coredata.usecase.TestAddUseCase
import com.lok.dev.coredatabase.entity.OMRTable
import com.lok.dev.coredatabase.entity.SubjectTable
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
class SettingViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getSubjectListUseCase: GetSubjectListUseCase,
    private val addSubjectUseCase: AddSubjectUseCase,
    private val addTestUseCase: TestAddUseCase
) : BaseViewModel() {

    private val _subjectState = MutableSharedFlow<SubjectState>()
    val subjectState = _subjectState.asSharedFlow()

    private val _subjectListData = mutableResultState<List<SubjectTable>>()
    val subjectListData = _subjectListData.asStateFlow()

    private val _subject = MutableStateFlow(SubjectTable(0, "과목을 선택하세요"))
    val subject = _subject.asStateFlow()

    fun setSubjectState(state: SubjectState) = viewModelScope.launch {
        _subjectState.emit(state)
    }

    fun setSubject(subjectTable: SubjectTable) {
        _subject.value = subjectTable
    }

    fun getSubjectList() {
        getSubjectListUseCase.invoke().onState(viewModelScope) {
            _subjectListData.value = it
        }
    }

    fun addSubject(name: String) = CoroutineScope(ioDispatcher).launch {
        val table = SubjectTable(name = name)
        addSubjectUseCase.invoke(table)
    }

    fun addOmrTest(title: String, problemNum: Int, selectNum: Int) = CoroutineScope(ioDispatcher).launch {
        val omrTable = OMRTable(
            subject = subject.value,
            title = title,
            problemNum = problemNum,
            selectNum = selectNum
        )
        addTestUseCase.invoke(omrTable)
    }
}