package com.lok.dev.coredatabase.dao

import androidx.room.*
import com.lok.dev.coredatabase.entity.*
import kotlinx.coroutines.flow.Flow
import java.sql.SQLException

@Dao
interface TagDao {

    @Query("SELECT * FROM TagTable")
    fun selectAllTagList(): Flow<List<TagTable>>

    @Throws(SQLException::class)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTag(tagTable: TagTable)

    @Query("DELETE FROM TagTable WHERE id in (:idList)")
    fun deleteTag(idList: List<Int>): Int

    @Update
    fun updateTag(tagTable: TagTable)

}