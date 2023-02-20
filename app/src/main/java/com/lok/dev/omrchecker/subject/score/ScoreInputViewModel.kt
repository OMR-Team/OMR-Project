package com.lok.dev.omrchecker.subject.score

import com.lok.dev.commonbase.BaseViewModel
import com.lok.dev.coredatabase.entity.AnswerTable
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScoreInputViewModel @Inject constructor(): BaseViewModel() {

    var scoreInputList = mutableListOf<AnswerTable>()

    fun updateScore(data: Pair<Int, Double>) {
        val problemIndex = data.first.minus(1)
        val score = data.second
        val modScore = scoreInputList[problemIndex].copy(score = score)
        scoreInputList[problemIndex] = modScore
    }
}