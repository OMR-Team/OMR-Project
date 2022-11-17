package com.example.coredatabase.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.coredatabase.converter.IntListTypeConverter

@Entity(primaryKeys = ["id", "problemNum"])
@TypeConverters(
    value = [
        IntListTypeConverter::class
    ]
)
data class AnswerTable(
    val id : Long = 0,
    val problemNum : Int,
    @ColumnInfo(name = "answer") val answer : List<Int>,
    @ColumnInfo(name = "problem_score") val problemScore : Double
)