package com.lok.dev.coredata.usecase

import com.lok.dev.coredata.repository.TagDatabaseRepository
import com.lok.dev.coredatabase.entity.TagTable
import javax.inject.Inject

class UpdateTagUseCase @Inject constructor(
    private val tagDatabaseRepository: TagDatabaseRepository
) {

    fun invoke(tagTable: TagTable) = tagDatabaseRepository.updateTag(tagTable)

}