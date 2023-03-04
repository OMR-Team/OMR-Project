package com.lok.dev.commonmodel.state

sealed class SubjectState {
    object List: SubjectState()
    object Add: SubjectState()
    object Exit: SubjectState()
}