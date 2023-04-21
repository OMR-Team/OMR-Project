package com.lok.dev.coredata.usecase

import com.lok.dev.coredata.repository.OMRDatabaseRepository
import javax.inject.Inject

class GetOmrMaxIdUseCase @Inject constructor(
    private val omrDatabaseRepository: OMRDatabaseRepository
) {
    suspend operator fun invoke() =
        omrDatabaseRepository.getMaxId()
}