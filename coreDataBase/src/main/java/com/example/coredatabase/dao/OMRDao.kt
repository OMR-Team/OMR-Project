package com.example.coredatabase.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.coredatabase.entity.AnswerTable
import com.example.coredatabase.entity.HistoryTable
import com.example.coredatabase.entity.OMRTable
import com.example.coredatabase.entity.TestTable
import kotlinx.coroutines.flow.Flow

@Dao
interface OMRDao {

    @Query("SELECT * FROM OMRTable")
    fun selectAllOMRTable() : Flow<OMRTable>

    @Query("SELECT * FROM TESTTABLE")
    fun selectAllTestTable() : Flow<TestTable>

    @Query("SELECT * FROM ANSWERTABLE")
    fun selectAllAnswerTable() : Flow<AnswerTable>

    @Query("SELECT * FROM HISTORYTABLE")
    fun selectAllHistoryTable() : Flow<HistoryTable>

}