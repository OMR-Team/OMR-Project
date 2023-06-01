package com.lok.dev.omrchecker.setting.viewmodel

import com.lok.dev.commonbase.BaseViewModel
import com.lok.dev.commonmodel.state.ResultUiState
import com.lok.dev.commonmodel.state.mutableResultState
import com.lok.dev.commonutil.di.IoDispatcher
import com.lok.dev.coredata.usecase.AddOmrUseCase
import com.lok.dev.coredata.usecase.GetOmrMaxIdUseCase
import com.lok.dev.coredata.usecase.GetTagListUseCase
import com.lok.dev.coredatabase.entity.OMRTable
import com.lok.dev.coredatabase.entity.SubjectTable
import com.lok.dev.coredatabase.entity.TagTable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getOmrMaxIdUseCase: GetOmrMaxIdUseCase,
    private val addOmrUseCase: AddOmrUseCase,
    private val getTagListUseCase: GetTagListUseCase,
) : BaseViewModel() {

    private val _subjectData = MutableStateFlow(SubjectTable(0, 0, "과목을 선택하세요"))
    val subjectData = _subjectData.asStateFlow()

    private val _maxId = MutableSharedFlow<Int?>()
    val maxId = _maxId.asSharedFlow()

    private val _selectTagList = mutableResultState<List<TagTable>>()
    val selectTagList = _selectTagList.asStateFlow()

    private var tag: List<TagTable> = listOf()

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
            selectNum = selectNum,
            tag = tag.map { it.id }
        )
        addOmrUseCase.invoke(omrTable)
        callBack.invoke(omrTable)
    }

    fun getTagList(tagList: IntArray) {
        getTagListUseCase.invoke().onEach { list ->
            tag = list.filter { it.id in tagList }
            _selectTagList.value = ResultUiState.Success(tag)
        }.catch {
            _selectTagList.value = ResultUiState.Error(it)
        }.launchIn(CoroutineScope(ioDispatcher))
    }
}