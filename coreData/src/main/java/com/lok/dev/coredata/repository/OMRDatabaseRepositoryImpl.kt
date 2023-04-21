package com.lok.dev.coredata.repository

import com.lok.dev.coredatabase.dao.OMRDao
import com.lok.dev.coredatabase.entity.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OMRDatabaseRepositoryImpl @Inject constructor(
    private val omrDao: OMRDao
) : OMRDatabaseRepository {
    override suspend fun getMaxId():Int? = omrDao.selectMaxId()
    override fun getOMRTable(): Flow<List<OMRTable>> = omrDao.selectAllOMRTable()
    override fun getOMRTableBySubject(subjectId: Int, isTemp: Boolean): Flow<List<OMRTable>> =
        omrDao.selectOMRTableBySubject(subjectId, isTemp)

    override fun addOMRTable(omrTable: OMRTable): Long = omrDao.addOMRTable(omrTable)
    override fun updateOMRTable(omrTable: OMRTable) = omrDao.updateOMRTable(omrTable)
}