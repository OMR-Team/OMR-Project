package com.lok.dev.coredatabase.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import com.lok.dev.coredatabase.converter.IntListTypeConverter

@Entity(primaryKeys = ["id", "problemNum"])
@TypeConverters(
    value = [
        IntListTypeConverter::class
    ]
)
data class TestTable(
    val id : Long = 0,
    val problemNum : Int,
    @ColumnInfo(name = "answer") val answer : List<Int>
)