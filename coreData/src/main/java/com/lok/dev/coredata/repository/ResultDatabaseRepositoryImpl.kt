package com.lok.dev.coredata.repository

import com.lok.dev.commonmodel.ResultJoinData
import com.lok.dev.coredatabase.dao.ResultDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ResultDatabaseRepositoryImpl @Inject constructor(
    private val resultDao: ResultDao
) : ResultDatabaseRepository {

    override fun getResultJoinTable(id: Int, no: Int): Flow<List<ResultJoinData>> = resultDao.getResultJoinTable(id, no)
}