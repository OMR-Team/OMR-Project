package com.lok.dev.coredata.repository

import com.lok.dev.coredatabase.entity.*
import kotlinx.coroutines.flow.Flow

interface OMRDatabaseRepository {

    fun getOMRTable(): Flow<List<OMRTable>>
    fun addOMRTable(omrTable: OMRTable): Long
    fun updateOMRTable(omrTable: OMRTable)
    fun getSubjectTable(): Flow<SubjectTable>
    fun getTagTable(): Flow<TagTable>
    fun getProblemTable(): Flow<ProblemTable>
    fun getAnswerTable(): Flow<AnswerTable>
    fun getHistoryTable(): Flow<HistoryTable>

}