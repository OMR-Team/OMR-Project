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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getSubjectListUseCase: GetSubjectListUseCase,
    private val addSubjectUseCase: AddSubjectUseCase
) : BaseViewModel() {

    private val _subjectState = MutableStateFlow<SubjectState>(SubjectState.None)
    val subjectState = _subjectState.asStateFlow()

    private val _subjectListData = mutableResultState<List<SubjectTable>>()
    val subjectListData = _subjectListData.asStateFlow()

    fun setSubjectState(state: SubjectState) {
        _subjectState.value = state
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
}