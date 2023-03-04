package com.lok.dev.coredata.usecase

import com.lok.dev.coredata.repository.AnswerDatabaseRepository
import com.lok.dev.coredatabase.entity.AnswerTable
import javax.inject.Inject

class AddAnswerUseCase @Inject constructor(
    private val addAnswerDatabaseRepository: AnswerDatabaseRepository
) {
    operator fun invoke(answerTable: AnswerTable) = addAnswerDatabaseRepository.addAnswerTable(answerTable)
}