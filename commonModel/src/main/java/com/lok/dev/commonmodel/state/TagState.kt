package com.lok.dev.commonmodel.state

sealed class TagState {
    object CHOOSE: TagState()
    object EDIT: TagState()
    data class Finish(val choose: List<Int> = listOf()): TagState()
    data class Remove(val type:String): TagState()
}