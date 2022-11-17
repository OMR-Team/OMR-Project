package com.example.coredata.usecase.repository

import com.example.coredatabase.dao.OMRDao
import com.example.coredatabase.entity.AnswerTable
import com.example.coredatabase.entity.HistoryTable
import com.example.coredatabase.entity.OMRTable
import com.example.coredatabase.entity.TestTable
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