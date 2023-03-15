package com.lok.dev.coredata.usecase

import com.lok.dev.coredata.repository.OMRDatabaseRepository
import com.lok.dev.coredatabase.entity.SubjectTable
import javax.inject.Inject

class GetOmrListUseCase @Inject constructor(
    private val omrDatabaseRepository: OMRDatabaseRepository
) {
    operator fun invoke(subject: SubjectTable) = omrDatabaseRepository.getOMRTableBySubject(subject)
}