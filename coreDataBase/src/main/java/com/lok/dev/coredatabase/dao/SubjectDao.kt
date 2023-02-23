package com.lok.dev.coredatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lok.dev.coredatabase.entity.SubjectTable
import kotlinx.coroutines.flow.Flow
import java.sql.SQLException

@Dao
interface SubjectDao {

    @Query("SELECT * FROM SubjectTable")
    fun selectAllSubjectList(): Flow<List<SubjectTable>>

    @Throws(SQLException::class)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSubject(tagTable: SubjectTable)

}