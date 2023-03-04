package com.lok.dev.coredatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lok.dev.coredatabase.entity.AnswerTable
import kotlinx.coroutines.flow.Flow
import java.sql.SQLException

@Dao
interface AnswerDao {

    @Throws(SQLException::class)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAnswerTable(answerTable: AnswerTable): Long

    @Query("SELECT * FROM AnswerTable WHERE id = :id")
    fun getAnswerTable(id: Int): Flow<List<AnswerTable>>

}