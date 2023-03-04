package com.lok.dev.omrchecker.setting.viewmodel

import com.lok.dev.commonbase.BaseViewModel
import com.lok.dev.commonutil.di.IoDispatcher
import com.lok.dev.coredata.usecase.AddOmrUseCase
import com.lok.dev.coredatabase.entity.OMRTable
import com.lok.dev.coredatabase.entity.SubjectTable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val addOmrUseCase: AddOmrUseCase
) : BaseViewModel() {

    private val _subjectData = MutableStateFlow(SubjectTable(0, 0, "과목을 선택하세요"))
    val subjectData = _subjectData.asStateFlow()

    fun setSubject(subjectTable: SubjectTable) {
        _subjectData.value = subjectTable
    }

    fun addOmrTest(title: String, problemNum: Int, selectNum: Int, callBack: (OMRTable) -> Unit) = CoroutineScope(ioDispatcher).launch {
        val omrTable = OMRTable(
            subject = subjectData.value,
            title = title,
            problemNum = problemNum,
            selectNum = selectNum
        )
        val id = addOmrUseCase.invoke(omrTable)
        omrTable.id = id.toInt()
        callBack.invoke(omrTable)
    }
}