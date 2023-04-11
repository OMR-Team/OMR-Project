package com.lok.dev.coredata.repository

import com.lok.dev.coredatabase.entity.HistoryTable
import kotlinx.coroutines.flow.Flow

interface HistoryDatabaseRepository {

    fun addHistoryTable(historyTable: HistoryTable): Long

    fun getHistoryTable(id: Int, cnt: Int): Flow<HistoryTable>
}