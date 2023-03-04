package com.lok.dev.coredata.usecase

import com.lok.dev.coredata.repository.OMRDatabaseRepository
import com.lok.dev.coredatabase.entity.OMRTable
import javax.inject.Inject

class UpdateOmrUseCase @Inject constructor(
    private val omrDatabaseRepository: OMRDatabaseRepository
) {
    operator fun invoke(omrTable: OMRTable) = omrDatabaseRepository.updateOMRTable(omrTable)
}