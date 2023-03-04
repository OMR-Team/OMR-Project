package com.lok.dev.coredatabase.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class SubjectTable(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "folderId") val folderId: Int = 1,
    @ColumnInfo(name = "name") val name: String = "",
) : Parcelable