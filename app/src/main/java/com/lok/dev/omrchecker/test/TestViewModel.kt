package com.lok.dev.omrchecker.test

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.lok.dev.commonbase.BaseViewModel
import com.lok.dev.commonmodel.state.mutableResultState
import com.lok.dev.commonutil.di.IoDispatcher
import com.lok.dev.commonutil.onState
import com.lok.dev.coredata.usecase.AddOmrUseCase
import com.lok.dev.coredata.usecase.TestUseCase
import com.lok.dev.coredatabase.entity.OMRTable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val testUseCase: TestUseCase,
    private val addTestUseCase: AddOmrUseCase
) : BaseViewModel() {

    private val _testState = mutableResultState<List<OMRTable>>()
    val testState = _testState.asStateFlow()

    fun getTestInfo() {
        testUseCase.invoke().onState(viewModelScope) {
            _testState.value = it
        }
    }

    fun addTestInfo(omrTable: OMRTable) {
        CoroutineScope(ioDispatcher).launch {
            try {
                addTestUseCase.invoke(omrTable = omrTable)
            } catch (error: Exception) {
                Log.d("123123123", error.toString())
            }
        }
    }

}