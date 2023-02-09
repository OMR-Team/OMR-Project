package com.lok.dev.coredata.usecase

import com.lok.dev.coredata.repository.TagDatabaseRepository
import com.lok.dev.coredatabase.entity.TagTable
import javax.inject.Inject

class AddTagUseCase @Inject constructor(
    private val tagDatabaseRepository: TagDatabaseRepository
) {

    fun invoke(tagTable: TagTable) = tagDatabaseRepository.addTag(tagTable)

}