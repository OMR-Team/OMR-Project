package com.lok.dev.omrchecker.subject

import androidx.lifecycle.viewModelScope
import com.lok.dev.commonbase.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OmrViewModel @Inject constructor() : BaseViewModel() {

    var omrInputList = listOf<List<Int>>()

    private val _screenState = MutableSharedFlow<OmrActivity.OmrState>()
    val screenState = _screenState.asSharedFlow()

    private val _omrInput = MutableSharedFlow<List<List<Int>>>()
    val omrInput = _omrInput.asSharedFlow()

    fun changeScreenState(state: OmrActivity.OmrState){
        viewModelScope.launch {
            _screenState.emit(state)
        }
    }

    fun changeOmrInput(list: List<List<Int>>) {
        viewModelScope.launch {
            _omrInput.emit(list)
        }
        omrInputList = list
    }

}