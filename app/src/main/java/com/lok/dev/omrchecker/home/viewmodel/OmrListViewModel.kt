package com.lok.dev.omrchecker.home.viewmodel

import androidx.lifecycle.viewModelScope
import com.lok.dev.commonbase.BaseViewModel
import com.lok.dev.commonmodel.state.mutableResultState
import com.lok.dev.commonutil.onState
import com.lok.dev.coredata.usecase.GetOmrListUseCase
import com.lok.dev.coredatabase.entity.OMRTable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class OmrListViewModel @Inject constructor(
    private val getOmrListUseCase: GetOmrListUseCase
) : BaseViewModel() {

    private val _omrListData = mutableResultState<List<OMRTable>>()
    val omrListData = _omrListData.asStateFlow()

    fun getOmrList(subjectId: Int) {
        getOmrListUseCase.invoke(subjectId).onState(viewModelScope) { result ->
            _omrListData.value = result
        }
    }
}