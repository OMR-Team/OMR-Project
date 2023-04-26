package com.lok.dev.coredatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lok.dev.coredatabase.entity.HistoryTable
import kotlinx.coroutines.flow.Flow
import java.sql.SQLException

@Dao
interface HistoryDao {

    @Throws(SQLException::class)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addHistoryTable(historyTable: HistoryTable): Long

    @Query("SELECT * FROM HistoryTable WHERE id = :id")
    fun getHistoryTable(id: Int): Flow<List<HistoryTable>>

}