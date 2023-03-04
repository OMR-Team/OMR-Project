package com.lok.dev.coredatabase.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.lok.dev.coredatabase.converter.IntListTypeConverter

@Entity(primaryKeys = ["id", "no"])
data class AnswerTable(
    val id : Int = 0,
    val no: Int = 0,
    @ColumnInfo(name = "answer") val answer : List<Int> = listOf(),
    @ColumnInfo(name = "score") val score: Double? = null
)