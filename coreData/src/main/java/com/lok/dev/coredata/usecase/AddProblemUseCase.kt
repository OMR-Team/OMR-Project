package com.lok.dev.coredata.usecase

import com.lok.dev.coredata.repository.ProblemDatabaseRepository
import com.lok.dev.coredatabase.entity.ProblemTable
import javax.inject.Inject

class AddProblemUseCase @Inject constructor(
    private val problemDatabaseRepository: ProblemDatabaseRepository
) {
    operator fun invoke(problemTable: ProblemTable) = problemDatabaseRepository.addProblemTable(problemTable)
}