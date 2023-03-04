package com.lok.dev.coredata.repository

import com.lok.dev.coredatabase.dao.AnswerDao
import com.lok.dev.coredatabase.entity.AnswerTable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AnswerDatabaseRepositoryImpl @Inject constructor(
    private val answerDao: AnswerDao
) : AnswerDatabaseRepository {

    override fun addAnswerTable(answerTable: AnswerTable) = answerDao.addAnswerTable(answerTable)

    override fun getAnswerTable(id: Int): Flow<List<AnswerTable>> = answerDao.getAnswerTable(id)
}