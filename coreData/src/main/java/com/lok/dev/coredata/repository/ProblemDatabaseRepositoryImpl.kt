package com.lok.dev.coredata.repository

import com.lok.dev.coredatabase.dao.ProblemDao
import com.lok.dev.coredatabase.entity.ProblemTable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProblemDatabaseRepositoryImpl @Inject constructor(
    private val problemDao: ProblemDao
) : ProblemDatabaseRepository {

    override fun addProblemTable(problemTable: ProblemTable) = problemDao.addProblemTable(problemTable)

    override fun getProblemTable(id: Int): Flow<List<ProblemTable>> = problemDao.getProblemTable(id)
}