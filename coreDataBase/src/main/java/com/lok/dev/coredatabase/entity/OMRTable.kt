package com.lok.dev.coredatabase.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.lok.dev.commonutil.convertToDate
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(primaryKeys = ["id", "cnt"])
data class OMRTable(
    var id: Int = 0,                                    // 시험 id
    val cnt: Int = 1,                                   // 회차
    @ColumnInfo(name = "subjectId") val subjectId: Int,             // 폴더id
    @ColumnInfo(name = "title") val title: String = "",             // 시험명
    @ColumnInfo(name = "correctCnt") val correctCnt: Int = 0,       // 맞은 수
    @ColumnInfo(name = "problemNum") val problemNum: Int = 0,       // 문제 수
    @ColumnInfo(name = "selectNum") val selectNum: Int = 0,         // 선지 수
    @ColumnInfo(name = "tag") val tag: List<Int> = listOf(),        // 태그 목록
    @ColumnInfo(name = "isTemp") val isTemp: Boolean = false,       // 임시저장 여부
    @ColumnInfo(name = "page") val page: Int = 0,                   // OMR 화면
    @ColumnInfo(name = "updateDate") val updateDate: Long = System.currentTimeMillis(), // 최근 업데이트 날짜
): Parcelable {

    fun getDate() = updateDate.convertToDate()

}