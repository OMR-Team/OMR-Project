package com.lok.dev.coredata.repository

import com.lok.dev.coredatabase.entity.ProblemTable
import kotlinx.coroutines.flow.Flow

interface ProblemDatabaseRepository {

    fun addProblemTable(problemTable: ProblemTable) : Long

    fun getProblemTable(id: Int) : Flow<List<ProblemTable>>
}