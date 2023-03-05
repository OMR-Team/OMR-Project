package com.lok.dev.coredata.usecase

import com.lok.dev.coredata.repository.ProblemDatabaseRepository
import javax.inject.Inject

class GetProblemTableUseCase @Inject constructor(
    private val problemDatabaseRepository: ProblemDatabaseRepository
) {
    operator fun invoke(id: Int) = problemDatabaseRepository.getProblemTable(id)
}