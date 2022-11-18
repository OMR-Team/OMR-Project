package com.lok.dev.coredata.repository

import com.lok.dev.coredatabase.dao.OMRDao
import com.lok.dev.coredatabase.entity.AnswerTable
import com.lok.dev.coredatabase.entity.HistoryTable
import com.lok.dev.coredatabase.entity.OMRTable
import com.lok.dev.coredatabase.entity.TestTable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OMRDatabaseRepositoryImpl @Inject constructor(
    private val omrDao: OMRDao
) : OMRDatabaseRepository {

    override fun getOMRTable(): Flow<OMRTable> {
        return omrDao.selectAllOMRTable()
    }

    override fun getTestTable(): Flow<TestTable> {
        return omrDao.selectAllTestTable()
    }

    override fun getAnswerTable(): Flow<AnswerTable> {
        return omrDao.selectAllAnswerTable()
    }

    override fun getHistoryTable(): Flow<HistoryTable> {
        return omrDao.selectAllHistoryTable()
    }


}