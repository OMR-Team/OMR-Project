package com.lok.dev.coredata.repository

import com.lok.dev.coredatabase.dao.TagDao
import com.lok.dev.coredatabase.entity.TagTable
import javax.inject.Inject

class TagDatabaseRepositoryImpl @Inject constructor(
    private val tagDao: TagDao
) : TagDatabaseRepository {

    override fun getTagList() = tagDao.selectAllTagList()
    override fun addTag(tagTable: TagTable) = tagDao.addTag(tagTable)

}