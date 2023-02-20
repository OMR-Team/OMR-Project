package com.lok.dev.commonutil

import android.view.View
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun View.throttleFirst(periodMillis: Long): Flow<View> = callbackFlow {

    var lastTime = 0L

    this@throttleFirst.setOnClickListener {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastTime >= periodMillis) {
            lastTime = currentTime
            this.trySend(this@throttleFirst).isSuccess
        }
    }
    awaitClose { this@throttleFirst.setOnClickListener(null) }
}

fun View.visible(visible: Boolean = true): Boolean {
    visibility = if (visible) View.VISIBLE else View.GONE
    return visible
}

