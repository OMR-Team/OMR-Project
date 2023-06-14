package com.lok.dev.omrchecker.subject.score

import com.lok.dev.commonbase.BaseViewModel
import com.lok.dev.coredatabase.entity.AnswerTable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ScoreInputViewModel @Inject constructor(): BaseViewModel() {

    private val _scoreInputList = MutableStateFlow<List<AnswerTable>>(listOf())
    val scoreInputList = _scoreInputList.asStateFlow()
    val scoreMap = mutableMapOf<Int, Double>()

    private val _scoreState = MutableStateFlow(0.0)
    val scoreState = _scoreState.asStateFlow()

    fun setScoreInputList(list : List<AnswerTable>) {
        _scoreInputList.value = list
    }

    fun updateScore(data: Pair<Int, Double>) {
        scoreMap[data.first] = data.second
        _scoreState.value = scoreMap.values.sum()
    }

    fun changeAllScore(score: Double) {
        val modList = scoreInputList.value.toMutableList()
        modList.forEachIndexed { index, data ->
            modList[index] = data.copy(score = score)
            scoreMap[index] = score
        }
        _scoreInputList.value = modList
    }
}