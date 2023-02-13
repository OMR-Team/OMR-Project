package com.lok.dev.commonutil

import java.text.SimpleDateFormat
import java.util.*

fun getToday(): String {
    val currentTime = System.currentTimeMillis()
    val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
    return simpleDateFormat.format(currentTime)
}