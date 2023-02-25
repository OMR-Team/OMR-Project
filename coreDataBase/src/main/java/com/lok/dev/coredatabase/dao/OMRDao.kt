package com.lok.dev.coredatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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

    @Query("SELECT * FROM SubjectTable")
    fun selectAllSubjectTable(): Flow<SubjectTable>

    @Query("SELECT * FROM TagTable")
    fun selectAllTagTable(): Flow<TagTable>

    @Query("SELECT * FROM ProblemTable")
    fun selectAllProblemTable(): Flow<ProblemTable>

    @Query("SELECT * FROM AnswerTable")
    fun selectAllAnswerTable(): Flow<AnswerTable>

    @Query("SELECT * FROM HistoryTable")
    fun selectAllHistoryTable(): Flow<HistoryTable>

}