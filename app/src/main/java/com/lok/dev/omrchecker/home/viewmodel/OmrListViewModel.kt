package com.lok.dev.omrchecker.home.viewmodel

import androidx.lifecycle.viewModelScope
import com.lok.dev.commonbase.BaseViewModel
import com.lok.dev.commonmodel.state.ResultUiState
import com.lok.dev.commonmodel.state.mutableResultState
import com.lok.dev.coredata.usecase.GetOmrListUseCase
import com.lok.dev.coredata.usecase.GetOmrOngoingListUseCase
import com.lok.dev.coredatabase.entity.OMRTable
import com.lok.dev.coredatabase.entity.SubjectTable
import com.lok.dev.omrchecker.custom.SortStandard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class OmrListViewModel @Inject constructor(
    private val getOmrListUseCase: GetOmrListUseCase,
    private val getOmrOngoingListUseCase: GetOmrOngoingListUseCase
) : BaseViewModel() {

    private val _isOngoingListEmpty = MutableStateFlow<Boolean?>(null)
    val isOngoingListEmpty = _isOngoingListEmpty.asStateFlow()
    private val _omrOngoingListData = mutableResultState<List<OMRTable>>()
    val omrOngoingListData = _omrOngoingListData.asStateFlow()
    private val _omrDoneListData = mutableResultState<List<OMRTable>>()
    val omrDoneListData = _omrDoneListData.asStateFlow()

    fun getOmrList(subjectId: Int) {
        getOmrListUseCase.invoke(subjectId)
            .onEach {
                _omrDoneListData.value = ResultUiState.Success(it)
            }.catch {
                _omrDoneListData.value = ResultUiState.Error(it)
            }.launchIn(viewModelScope)
    }

    fun getOmrOngoingList(subjectId: Int) {
        getOmrOngoingListUseCase.invoke(subjectId)
            .onEach {
                _isOngoingListEmpty.value = it.isEmpty()
                _omrOngoingListData.value = ResultUiState.Success(it)
            }.catch {
                _omrOngoingListData.value = ResultUiState.Error(it)
            }.launchIn(viewModelScope)
    }

    fun sortSubjectList(standard: SortStandard) {
        if (omrDoneListData.value !is ResultUiState.Success) return
        val list = (omrDoneListData.value as ResultUiState.Success<List<OMRTable>>).copy()
        when (standard) {
            SortStandard.NEWEST -> {
                _omrDoneListData.value =
                    ResultUiState.Success(list.data.sortedByDescending { it.updateDate })
            }
            SortStandard.ALPHABET -> {
                _omrDoneListData.value = ResultUiState.Success(list.data.sortedBy { it.title })
            }
            SortStandard.ADD -> {
                _omrDoneListData.value =
                    ResultUiState.Success(list.data.sortedByDescending { it.updateDate })
            }
        }
    }
}