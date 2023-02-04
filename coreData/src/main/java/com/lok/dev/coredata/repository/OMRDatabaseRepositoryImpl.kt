package com.lok.dev.coredata.repository

import com.lok.dev.coredatabase.dao.OMRDao
import com.lok.dev.coredatabase.entity.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OMRDatabaseRepositoryImpl @Inject constructor(
    private val omrDao: OMRDao
) : OMRDatabaseRepository {
    override fun getOMRTable(): Flow<OMRTable> = omrDao.selectAllOMRTable()

    override fun getSubjectTable(): Flow<SubjectTable> = omrDao.selectAllSubjectTable()

    override fun getTagTable(): Flow<TagTable> = omrDao.selectAllTagTable()

    override fun getProblemTable(): Flow<ProblemTable> = omrDao.selectAllProblemTable()

    override fun getAnswerTable(): Flow<AnswerTable> = omrDao.selectAllAnswerTable()

    override fun getHistoryTable(): Flow<HistoryTable> = omrDao.selectAllHistoryTable()

}