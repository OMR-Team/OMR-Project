package com.lok.dev.coredatabase.dao

import androidx.room.Dao
import androidx.room.Query
import com.lok.dev.coredatabase.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface OMRDao {

    @Query("SELECT * FROM OMRTable")
    fun selectAllOMRTable(): Flow<OMRTable>

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