package com.lok.dev.coredata.repository

import com.lok.dev.coredatabase.dao.HistoryDao
import com.lok.dev.coredatabase.entity.HistoryTable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HistoryDatabaseRepositoryImpl @Inject constructor(
    private val historyDao: HistoryDao
): HistoryDatabaseRepository {

    override fun addHistoryTable(historyTable: HistoryTable) = historyDao.addHistoryTable(historyTable)

    override fun getHistoryTable(id: Int, cnt: Int): Flow<HistoryTable> = historyDao.getHistoryTable(id, cnt)
}