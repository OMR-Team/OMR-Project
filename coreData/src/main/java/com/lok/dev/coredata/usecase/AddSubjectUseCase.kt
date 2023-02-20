package com.lok.dev.coredata.usecase

import com.lok.dev.coredata.repository.SubjectDatabaseRepository
import com.lok.dev.coredatabase.entity.SubjectTable
import javax.inject.Inject

class AddSubjectUseCase @Inject constructor(
    private val subjectDatabaseRepository: SubjectDatabaseRepository
) {

    fun invoke(table: SubjectTable) = subjectDatabaseRepository.addSubject(table)
}