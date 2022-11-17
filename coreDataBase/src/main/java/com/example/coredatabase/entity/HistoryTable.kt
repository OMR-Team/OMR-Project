package com.example.coredatabase.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.coredatabase.converter.IntListTypeConverter

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