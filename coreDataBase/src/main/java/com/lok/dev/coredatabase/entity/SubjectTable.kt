package com.lok.dev.coredatabase.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class SubjectTable(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "folderId") val folderId: Int = 1,
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "updateDate") val updateDate: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "addDate") val addDate: Long = System.currentTimeMillis(),
) : Parcelable