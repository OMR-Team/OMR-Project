package com.lok.dev.coredata.repository

import android.util.Log
import com.lok.dev.coredatabase.dao.TagDao
import com.lok.dev.coredatabase.entity.TagTable
import javax.inject.Inject

class TagDatabaseRepositoryImpl @Inject constructor(
    private val tagDao: TagDao
) : TagDatabaseRepository {

    override fun getTagList() = tagDao.selectAllTagList()
    override fun addTag(tagTable: TagTable) = tagDao.addTag(tagTable)
    override fun deleteTag(idList: List<Int>) = tagDao.deleteTag(idList)
    override fun updateTag(tagTable: TagTable) = tagDao.updateTag(tagTable)

}