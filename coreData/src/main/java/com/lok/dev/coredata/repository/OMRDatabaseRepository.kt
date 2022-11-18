package com.lok.dev.coredata.repository

import com.lok.dev.coredatabase.entity.AnswerTable
import com.lok.dev.coredatabase.entity.HistoryTable
import com.lok.dev.coredatabase.entity.OMRTable
import com.lok.dev.coredatabase.entity.TestTable
import kotlinx.coroutines.flow.Flow

interface OMRDatabaseRepository {

    fun getOMRTable() : Flow<OMRTable>
    fun getTestTable() : Flow<TestTable>
    fun getAnswerTable() : Flow<AnswerTable>
    fun getHistoryTable() : Flow<HistoryTable>

}