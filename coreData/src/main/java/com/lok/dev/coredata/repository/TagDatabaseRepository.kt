package com.lok.dev.coredata.repository

import com.lok.dev.coredatabase.entity.TagTable
import kotlinx.coroutines.flow.Flow

interface TagDatabaseRepository {
    fun getTagList(): Flow<List<TagTable>>
    fun addTag(tagTable: TagTable)
}