package com.lok.dev.coredata.repository

import com.lok.dev.coredatabase.entity.SubjectTable
import kotlinx.coroutines.flow.Flow

interface SubjectDatabaseRepository {
    fun getSubjectList(): Flow<List<SubjectTable>>
    fun addSubject(subjectTable: SubjectTable)
    fun updateSubjectTable(subjectTable: SubjectTable)
}