package com.lok.dev.omrchecker.home.viewmodel

import androidx.lifecycle.viewModelScope
import com.lok.dev.commonbase.BaseViewModel
import com.lok.dev.commonmodel.state.ResultUiState
import com.lok.dev.commonmodel.state.mutableResultState
import com.lok.dev.commonutil.preference.PreferenceUtil
import com.lok.dev.coredata.usecase.GetSubjectListUseCase
import com.lok.dev.coredatabase.entity.SubjectTable
import com.lok.dev.omrchecker.custom.SortStandard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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
        getSubjectListUseCase.invoke()
            .onStart {
                _subjectListData.value = ResultUiState.Loading
            }.mapLatest {
                _subjectListData.value = ResultUiState.Success(it)
            }.launchIn(viewModelScope)
    }

    fun sortSubjectList(standard: SortStandard) {
        if (_subjectListData.value !is ResultUiState.Success) return
        val list = (_subjectListData.value as ResultUiState.Success<List<SubjectTable>>).copy()
        when (standard) {
            SortStandard.NEWEST -> {
                _subjectListData.value =
                    ResultUiState.Success(list.data.sortedByDescending { it.updateDate })
            }
            SortStandard.ALPHABET -> {
                _subjectListData.value = ResultUiState.Success(list.data.sortedBy { it.name })
            }
            SortStandard.ADD -> {
                _subjectListData.value =
                    ResultUiState.Success(list.data.sortedByDescending { it.addDate })
            }
        }
    }
}