package com.lok.dev.coredatabase.dao

import androidx.room.*
import com.lok.dev.coredatabase.entity.*
import kotlinx.coroutines.flow.Flow
import java.sql.SQLException

@Dao
interface OMRDao {

    @Query("SELECT MAX(id) FROM OMRTable")
    suspend fun selectMaxId(): Int?

    @Query("SELECT * FROM OMRTable")
    fun selectAllOMRTable(): Flow<List<OMRTable>>

    @Query("SELECT * FROM OMRTable WHERE subjectId = :subjectId AND isTemp = :isTemp ORDER BY updateDate DESC")
    fun selectOMRTableBySubject(subjectId: Int, isTemp: Boolean): Flow<List<OMRTable>>

    @Throws(SQLException::class)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOMRTable(omrTable: OMRTable): Long

    @Update
    fun updateOMRTable(omrTable: OMRTable)
}