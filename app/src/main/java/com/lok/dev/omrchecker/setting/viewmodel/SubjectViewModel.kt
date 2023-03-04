package com.lok.dev.omrchecker.setting.viewmodel

import androidx.lifecycle.viewModelScope
import com.lok.dev.commonbase.BaseViewModel
import com.lok.dev.commonmodel.state.SubjectState
import com.lok.dev.commonmodel.state.mutableResultState
import com.lok.dev.commonutil.di.IoDispatcher
import com.lok.dev.commonutil.onState
import com.lok.dev.coredata.usecase.AddSubjectUseCase
import com.lok.dev.coredata.usecase.GetSubjectListUseCase
import com.lok.dev.coredatabase.entity.SubjectTable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubjectViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getSubjectListUseCase: GetSubjectListUseCase,
    private val addSubjectUseCase: AddSubjectUseCase,
) : BaseViewModel() {

    private val _subjectState = MutableSharedFlow<SubjectState>()
    val subjectState = _subjectState.asSharedFlow()

    private val _subjectListData = mutableResultState<List<SubjectTable>>()
    val subjectListData = _subjectListData.asStateFlow()

    var subjectData = SubjectTable()

    fun setSubjectState(state: SubjectState) = viewModelScope.launch {
        _subjectState.emit(state)
    }

    fun getSubjectList() {
        getSubjectListUseCase.invoke().onState(viewModelScope) {
            _subjectListData.value = it
        }
    }

    fun addSubject(folderId: Int, name: String) = CoroutineScope(ioDispatcher).launch {
        val table = SubjectTable(folderId = folderId, name = name)
        addSubjectUseCase.invoke(table)
    }
}