package com.example.coredatabase.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.coredatabase.dao.OMRDao
import com.example.coredatabase.entity.AnswerTable
import com.example.coredatabase.entity.HistoryTable
import com.example.coredatabase.entity.OMRTable
import com.example.coredatabase.entity.TestTable

@Database(entities = [OMRTable::class, TestTable::class, AnswerTable::class, HistoryTable::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getOMRDao() : OMRDao
}