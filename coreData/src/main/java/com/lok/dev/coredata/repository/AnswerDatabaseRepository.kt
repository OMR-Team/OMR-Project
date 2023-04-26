package com.lok.dev.coredata.repository

import com.lok.dev.coredatabase.entity.AnswerTable
import kotlinx.coroutines.flow.Flow

interface AnswerDatabaseRepository {

    fun addAnswerTable(answerTable: AnswerTable): Long

    suspend fun getAnswerTable(id: Int) : List<AnswerTable>
}