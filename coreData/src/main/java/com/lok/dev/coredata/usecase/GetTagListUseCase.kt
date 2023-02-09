package com.lok.dev.coredata.usecase

import com.lok.dev.coredata.repository.TagDatabaseRepository
import javax.inject.Inject

class GetTagListUseCase @Inject constructor(
    private val tagDatabaseRepository: TagDatabaseRepository
) {

    fun invoke() = tagDatabaseRepository.getTagList()

}