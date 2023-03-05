package com.lok.dev.omrchecker.home.viewmodel

import androidx.lifecycle.viewModelScope
import com.lok.dev.commonbase.BaseViewModel
import com.lok.dev.commonmodel.state.mutableResultState
import com.lok.dev.commonutil.onState
import com.lok.dev.commonutil.preference.PreferenceUtil
import com.lok.dev.coredata.usecase.GetSubjectListUseCase
import com.lok.dev.coredatabase.entity.SubjectTable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FolderListViewModel @Inject constructor(
    private val preferenceUtil: PreferenceUtil,
    private val getSubjectListUseCase: GetSubjectListUseCase,
) : BaseViewModel() {

    private val _folderState = MutableStateFlow(preferenceUtil.folderState)
    val folderState = _folderState.asStateFlow()
    private val _subjectListData = mutableResultState<List<SubjectTable>>()
    val subjectListData = _subjectListData.asStateFlow()

    fun updateFolderState(stateOrdinal: Int) {
        _folderState.value = stateOrdinal
        preferenceUtil.putFolderState(stateOrdinal)
    }

    fun getFolderList() {
        getSubjectListUseCase.invoke().onState(viewModelScope) {
            _subjectListData.value = it
        }
    }
}