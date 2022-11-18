package com.lok.dev.coredatabase.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import com.lok.dev.coredatabase.converter.IntListTypeConverter

@Entity(primaryKeys = ["id", "historyNum"])
@TypeConverters(
    value = [
        IntListTypeConverter::class
    ]
)
data class HistoryTable (
    val id : Long,
    val historyNum : Int,
    @ColumnInfo(name = "answer") val answer : List<Int>,
    @ColumnInfo(name = "score") val score : Double
)