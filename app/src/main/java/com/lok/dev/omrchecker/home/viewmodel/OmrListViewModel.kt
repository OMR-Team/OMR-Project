package com.lok.dev.omrchecker.home.viewmodel

import androidx.lifecycle.viewModelScope
import com.lok.dev.commonbase.BaseViewModel
import com.lok.dev.commonmodel.state.ResultUiState
import com.lok.dev.commonmodel.state.mutableResultState
import com.lok.dev.coredata.usecase.GetOmrListUseCase
import com.lok.dev.coredata.usecase.GetOmrOngoingListUseCase
import com.lok.dev.coredatabase.entity.OMRTable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class OmrListViewModel @Inject constructor(
    private val getOmrListUseCase: GetOmrListUseCase,
    private val getOmrOngoingListUseCase: GetOmrOngoingListUseCase
) : BaseViewModel() {

    private val _omrOngoingListData = mutableResultState<List<OMRTable>>()
    val omrOngoingListData = _omrOngoingListData.asStateFlow()
    private val _omrListData = mutableResultState<List<OMRTable>>()
    val omrListData = _omrListData.asStateFlow()

    fun getOmrList(subjectId: Int) {
        getOmrListUseCase.invoke(subjectId)
            .onEach {
                _omrListData.value = ResultUiState.Success(it)
            }.catch {
                _omrListData.value = ResultUiState.Error(it)
            }.launchIn(viewModelScope)
    }

    fun getOmrOngoingList(subjectId: Int) {
        getOmrOngoingListUseCase.invoke(subjectId)
            .onEach {
                _omrOngoingListData.value = ResultUiState.Success(it)
            }.catch {
                _omrOngoingListData.value = ResultUiState.Error(it)
            }.launchIn(viewModelScope)
    }
}