package com.lok.dev.coredatabase.dao

import androidx.room.*
import com.lok.dev.coredatabase.entity.*
import kotlinx.coroutines.flow.Flow
import java.sql.SQLException

@Dao
interface OMRDao {

    @Query("SELECT * FROM OMRTable")
    fun selectAllOMRTable(): Flow<List<OMRTable>>

    @Throws(SQLException::class)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOMRTable(omrTable: OMRTable): Long

    @Update
    fun updateOMRTable(omrTable: OMRTable)
}