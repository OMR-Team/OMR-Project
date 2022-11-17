package com.example.coredatabase.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["id, problemNum"])
data class AnswerTable(
    @PrimaryKey(autoGenerate = true) val id : Long = 0,
    @PrimaryKey(autoGenerate = false) val problemNum : Int,
    @ColumnInfo(name = "answer") val answer : List<Int>,
    @ColumnInfo(name = "problem_score") val problemScore : Double
)