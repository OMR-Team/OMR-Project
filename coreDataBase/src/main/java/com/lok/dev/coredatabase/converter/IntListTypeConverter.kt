package com.lok.dev.coredatabase.converter

import androidx.room.TypeConverter
import com.google.gson.Gson

class IntListTypeConverter() {

    @TypeConverter
    fun listToJson(value : List<Int>) : String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value : String) : List<Int> {
        return Gson().fromJson(value, Array<Int>::class.java).toList()
    }


}