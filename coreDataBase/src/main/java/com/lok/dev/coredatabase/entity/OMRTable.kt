package com.lok.dev.coredatabase.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.lok.dev.coredatabase.converter.IntListTypeConverter

@Entity
data class OMRTable(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "subject") val subject: Long = 0,
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "problemNum") val problemNum: Int = 0,
    @ColumnInfo(name = "selectNum") val selectNum: Int = 0,
    @ColumnInfo(name = "tag") val tag: List<Int> = listOf()
)