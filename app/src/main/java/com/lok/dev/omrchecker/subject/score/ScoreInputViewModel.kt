package com.lok.dev.omrchecker.subject.score

import com.lok.dev.commonbase.BaseViewModel
import com.lok.dev.coredatabase.entity.AnswerTable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ScoreInputViewModel @Inject constructor(): BaseViewModel() {

    var scoreInputList = mutableListOf<AnswerTable>()
    val scoreMap = mutableMapOf<Int, Double>()

    private val _scoreState = MutableStateFlow(0.0)
    val scoreState = _scoreState.asStateFlow()

    fun updateScore(data: Pair<Int, Double>) {
        scoreMap[data.first] = data.second
        _scoreState.value = scoreMap.values.sum()
    }

    fun changeAllScore(score: Double) {
        scoreInputList.forEachIndexed { index, data ->
            scoreInputList[index] = data.copy(score = score)
            scoreMap[index.plus(1)] = score
        }
    }
}