package com.lok.dev.omrchecker.subject

import androidx.lifecycle.viewModelScope
import com.lok.dev.commonbase.BaseViewModel
import com.lok.dev.coredatabase.entity.ProblemTable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OmrViewModel @Inject constructor() : BaseViewModel() {

    private val problemSelected = mutableSetOf<Int>()

    //TODO 임시로 놔둠 나중에 액티비티 열때 받은 문제수로 바꾸기
    var problemNum = 50


    private val _progressState = MutableStateFlow(0)
    val progressState = _progressState.asStateFlow()

    private val _screenState = MutableSharedFlow<OmrActivity.OmrState>()
    val screenState = _screenState.asSharedFlow()

    private val _omrInput = MutableStateFlow<List<ProblemTable>>(listOf())
    val omrInput = _omrInput.asStateFlow()

    fun changeScreenState(state: OmrActivity.OmrState) {
        viewModelScope.launch {
            _screenState.emit(state)
        }
    }

    fun changeOmrInput(list: List<ProblemTable>) {
        _omrInput.value = list
    }

    fun updateProgress(pair: Pair<Boolean, Int>) {
        val isChecked = pair.first
        val problemNum = pair.second
        if(isChecked) problemSelected.add(problemNum)
        else problemSelected.remove(problemNum)
        _progressState.value = problemSelected.size
    }

}