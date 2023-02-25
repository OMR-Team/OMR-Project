package com.lok.dev.coredata.usecase

import com.lok.dev.coredata.repository.TagDatabaseRepository
import javax.inject.Inject

class DeleteTagUseCase @Inject constructor(
    private val tagDatabaseRepository: TagDatabaseRepository
) {

    fun invoke(idList: List<Int>) = tagDatabaseRepository.deleteTag(idList)

}