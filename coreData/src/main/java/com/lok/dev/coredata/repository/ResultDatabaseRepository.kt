package com.lok.dev.coredata.repository

import com.lok.dev.commonmodel.ResultJoinData
import kotlinx.coroutines.flow.Flow

interface ResultDatabaseRepository {

    fun getResultJoinTable(id: Int, no: Int): Flow<List<ResultJoinData>>
}