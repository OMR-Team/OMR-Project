package com.lok.dev.coredata.usecase

import com.lok.dev.coredata.repository.SubjectDatabaseRepository
import javax.inject.Inject

class GetSubjectListUseCase @Inject constructor(
    private val subjectDatabaseRepository: SubjectDatabaseRepository
) {

    fun invoke() = subjectDatabaseRepository.getSubjectList()

}