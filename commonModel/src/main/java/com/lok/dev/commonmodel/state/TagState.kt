package com.lok.dev.commonmodel.state

sealed class TagState {
    object CHOOSE: TagState()
    object EDIT: TagState()
    object FINISH: TagState()
    data class Remove(val type:String): TagState()
}