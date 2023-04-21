package com.lok.dev.omrchecker.setting.viewmodel

import com.lok.dev.commonbase.BaseViewModel
import com.lok.dev.commonutil.di.IoDispatcher
import com.lok.dev.coredata.usecase.AddOmrUseCase
import com.lok.dev.coredata.usecase.GetOmrMaxIdUseCase
import com.lok.dev.coredatabase.entity.OMRTable
import com.lok.dev.coredatabase.entity.SubjectTable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getOmrMaxIdUseCase: GetOmrMaxIdUseCase,
    private val addOmrUseCase: AddOmrUseCase
) : BaseViewModel() {

    private val _subjectData = MutableStateFlow(SubjectTable(0, 0, "과목을 선택하세요"))
    val subjectData = _subjectData.asStateFlow()

    private val _maxId = MutableSharedFlow<Int?>()
    val maxId = _maxId.asSharedFlow()

    fun setSubject(subjectTable: SubjectTable) {
        _subjectData.value = subjectTable
    }

    fun getMaxId() = CoroutineScope(ioDispatcher).launch {
        _maxId.emit(getOmrMaxIdUseCase.invoke())
    }

    fun addOmrTest(id: Int, title: String, problemNum: Int, selectNum: Int, callBack: (OMRTable) -> Unit) = CoroutineScope(ioDispatcher).launch {
        val omrTable = OMRTable(
            id = id,
            subjectId = subjectData.value.id,
            title = title,
            problemNum = problemNum,
            selectNum = selectNum
        )
        addOmrUseCase.invoke(omrTable)
        callBack.invoke(omrTable)
    }
}