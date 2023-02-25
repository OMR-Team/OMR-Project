package com.lok.dev.commonmodel.state

sealed interface AnimationState {
    object None : AnimationState
    object Left : AnimationState
    object Right : AnimationState
    object Up : AnimationState
    object Down : AnimationState
    object Fade : AnimationState
}