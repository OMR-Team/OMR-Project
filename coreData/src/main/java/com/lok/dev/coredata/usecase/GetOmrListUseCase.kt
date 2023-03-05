package com.lok.dev.coredata.usecase

import com.lok.dev.coredata.repository.OMRDatabaseRepository
import javax.inject.Inject

class GetOmrListUseCase @Inject constructor(
    private val omrDatabaseRepository: OMRDatabaseRepository
) {
    operator fun invoke() = omrDatabaseRepository.getOMRTable()
}