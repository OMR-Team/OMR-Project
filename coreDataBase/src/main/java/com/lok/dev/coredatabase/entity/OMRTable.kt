package com.lok.dev.coredatabase.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OMRTable(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "subject") val subject: SubjectTable,
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "cnt") val cnt: Int = 1,
    @ColumnInfo(name = "correctCnt") val correctCnt: Int = 0,
    @ColumnInfo(name = "problemNum") val problemNum: Int = 0,
    @ColumnInfo(name = "selectNum") val selectNum: Int = 0,
    @ColumnInfo(name = "tag") val tag: List<Int> = listOf(),
    @ColumnInfo(name = "isTemp") val isTemp: Boolean = false,
    @ColumnInfo(name = "updateDate") val updateDate: Long = System.currentTimeMillis(),
)