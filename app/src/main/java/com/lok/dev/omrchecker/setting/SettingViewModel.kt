package com.lok.dev.omrchecker.setting

import com.lok.dev.commonbase.BaseViewModel
import com.lok.dev.commonmodel.state.SubjectState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(

) : BaseViewModel() {

    private val _subjectState = MutableStateFlow<SubjectState>(SubjectState.None)
    val subjectState = _subjectState.asStateFlow()

    fun setSubjectState(state: SubjectState) {
        _subjectState.value = state
    }
}