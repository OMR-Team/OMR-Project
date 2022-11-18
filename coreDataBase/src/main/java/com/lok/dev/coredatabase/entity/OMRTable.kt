package com.lok.dev.coredatabase.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OMRTable(
    @PrimaryKey(autoGenerate = true) val id : Long = 0,
    @ColumnInfo(name = "title") val title : String,
    @ColumnInfo(name = "problem_total") val problemTotal : Int,
    @ColumnInfo(name = "select_total") val selectTotal : Int
)