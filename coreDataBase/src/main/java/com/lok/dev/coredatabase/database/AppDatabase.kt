package com.lok.dev.coredatabase.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lok.dev.coredatabase.converter.IntListTypeConverter
import com.lok.dev.coredatabase.dao.OMRDao
import com.lok.dev.coredatabase.dao.TagDao
import com.lok.dev.coredatabase.entity.*

@Database(
    entities = [OMRTable::class, SubjectTable::class, TagTable::class, ProblemTable::class, AnswerTable::class, HistoryTable::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(IntListTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getOMRDao(): OMRDao
    abstract fun getTagDao(): TagDao
}