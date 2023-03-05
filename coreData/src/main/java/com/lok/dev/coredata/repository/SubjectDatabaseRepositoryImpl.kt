package com.lok.dev.coredata.repository

import com.lok.dev.coredatabase.dao.SubjectDao
import com.lok.dev.coredatabase.entity.SubjectTable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubjectDatabaseRepositoryImpl @Inject constructor(
    private val subjectDao: SubjectDao
) : SubjectDatabaseRepository {

    override fun getSubjectList(): Flow<List<SubjectTable>> = subjectDao.selectAllSubjectList()
    override fun addSubject(subjectTable: SubjectTable) = subjectDao.addSubject(subjectTable)
    override fun updateSubjectTable(subjectTable: SubjectTable) = subjectDao.updateSubject(subjectTable)
}