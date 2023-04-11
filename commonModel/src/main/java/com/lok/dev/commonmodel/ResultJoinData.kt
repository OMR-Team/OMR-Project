package com.lok.dev.commonmodel

data class ResultJoinData(
    val id: Int,
    val cnt: Int,
    val no: Int,
    val problem: List<Int>,
    val answer: List<Int>,
    val score: Double?
)