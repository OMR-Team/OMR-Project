package com.lok.dev.coredatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lok.dev.coredatabase.entity.ProblemTable
import kotlinx.coroutines.flow.Flow
import java.sql.SQLException

@Dao
interface ProblemDao {

    @Throws(SQLException::class)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProblemTable(problemTable: ProblemTable): Long

    @Query("SELECT * FROM ProblemTable WHERE id = :id")
    fun getProblemTable(id: Int): Flow<List<ProblemTable>>

}