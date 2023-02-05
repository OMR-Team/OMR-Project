package com.lok.dev.omrchecker.subject

import androidx.lifecycle.viewModelScope
import com.lok.dev.commonbase.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OmrViewModel @Inject constructor() : BaseViewModel() {


    private val _screenState = MutableSharedFlow<OmrActivity.OmrState>()
    val screenState = _screenState.asSharedFlow()

    fun changeScreenState(state: OmrActivity.OmrState){
        viewModelScope.launch {
            _screenState.emit(state)
        }
    }

}