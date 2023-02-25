package com.lok.dev.coredatabase.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.lok.dev.coredatabase.entity.SubjectTable

class SubjectTableTypeConverter {

    @TypeConverter
    fun subjectTableToJson(value : SubjectTable) : String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToSubjectTable(value : String) : SubjectTable {
        return Gson().fromJson(value, SubjectTable::class.java)
    }
}