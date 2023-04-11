package com.lok.dev.coredata.usecase

import com.lok.dev.coredata.repository.OMRDatabaseRepository
import javax.inject.Inject

class GetOmrOngoingListUseCase @Inject constructor(
    private val omrDatabaseRepository: OMRDatabaseRepository
) {
    operator fun invoke(subjectId: Int) =
        omrDatabaseRepository.getOMRTableBySubject(subjectId, true)
}