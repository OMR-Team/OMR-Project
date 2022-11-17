package com.example.coredata.usecase.repository

import com.example.coredatabase.entity.AnswerTable
import com.example.coredatabase.entity.HistoryTable
import com.example.coredatabase.entity.OMRTable
import com.example.coredatabase.entity.TestTable
import kotlinx.coroutines.flow.Flow

interface OMRDatabaseRepository {

    fun getOMRTable() : Flow<OMRTable>
    fun getTestTable() : Flow<TestTable>
    fun getAnswerTable() : Flow<AnswerTable>
    fun getHistoryTable() : Flow<HistoryTable>

}