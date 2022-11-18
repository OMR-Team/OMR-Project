package com.lok.dev.coredatabase.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lok.dev.coredatabase.dao.OMRDao
import com.lok.dev.coredatabase.entity.AnswerTable
import com.lok.dev.coredatabase.entity.HistoryTable
import com.lok.dev.coredatabase.entity.OMRTable
import com.lok.dev.coredatabase.entity.TestTable

@Database(entities = [OMRTable::class, TestTable::class, AnswerTable::class, HistoryTable::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getOMRDao() : OMRDao
}