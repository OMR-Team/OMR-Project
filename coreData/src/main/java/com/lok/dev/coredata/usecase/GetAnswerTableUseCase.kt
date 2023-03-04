package com.lok.dev.coredata.usecase

import com.lok.dev.coredata.repository.AnswerDatabaseRepository
import javax.inject.Inject

class GetAnswerTableUseCase @Inject constructor(
    private val answerDatabaseRepository: AnswerDatabaseRepository
) {
    operator fun invoke(id: Int) = answerDatabaseRepository.getAnswerTable(id)
}