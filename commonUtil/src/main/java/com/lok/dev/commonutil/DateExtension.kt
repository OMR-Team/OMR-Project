package com.lok.dev.commonutil

import java.text.SimpleDateFormat
import java.util.*

const val DATE_PATTERN = "yyyy.MM.dd"

fun getToday(pattern: String = DATE_PATTERN): String {
    val currentTime = System.currentTimeMillis()
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    return simpleDateFormat.format(currentTime)
}

fun Long.convertToDate(pattern: String = DATE_PATTERN): String {
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    return simpleDateFormat.format(this)
}