package com.lok.dev.coredatabase.dao

import androidx.room.*
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

    @Update
    fun updateSubject(subjectTable: SubjectTable)

}