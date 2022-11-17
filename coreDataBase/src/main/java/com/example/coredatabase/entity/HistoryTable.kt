package com.example.coredatabase.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["id", "historyNum"])
data class HistoryTable (
    @PrimaryKey(autoGenerate = true) val id : Long,
    @PrimaryKey(autoGenerate = false) val historyNum : Int,
    @ColumnInfo(name = "answer") val answer : List<Int>,
    @ColumnInfo(name = "score") val score : Double
)