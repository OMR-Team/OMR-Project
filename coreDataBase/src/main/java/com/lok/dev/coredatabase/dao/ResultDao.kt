package com.lok.dev.coredatabase.dao

import androidx.room.Dao
import androidx.room.Query
import com.lok.dev.commonmodel.ResultJoinData
import kotlinx.coroutines.flow.Flow

@Dao
interface ResultDao {

    @Query("SELECT a.id, a.cnt, a.`no`, a.answer as problem, b.answer as answer, b.score" +
            " FROM ProblemTable a " +
            " LEFT OUTER JOIN AnswerTable b " +
            "   ON (a.id = b.id AND a.`no` = b.`no`) " +
            "WHERE a.id = :id " +
            "  AND a.cnt = :no")
    fun getResultJoinTable(id: Int, no: Int): Flow<List<ResultJoinData>>
}