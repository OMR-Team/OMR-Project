package com.lok.dev.omrchecker.setting.viewmodel

import com.lok.dev.commonbase.BaseViewModel
import com.lok.dev.commonmodel.state.ResultUiState
import com.lok.dev.commonmodel.state.TagState
import com.lok.dev.commonmodel.state.mutableResultState
import com.lok.dev.commonutil.di.IoDispatcher
import com.lok.dev.coredata.usecase.AddTagUseCase
import com.lok.dev.coredata.usecase.DeleteTagUseCase
import com.lok.dev.coredata.usecase.GetTagListUseCase
import com.lok.dev.coredata.usecase.UpdateTagUseCase
import com.lok.dev.coredatabase.entity.TagTable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getTagListUseCase: GetTagListUseCase,
    private val addTagUseCase: AddTagUseCase,
    private val deleteTagUseCase: DeleteTagUseCase,
    private val updateTagUseCase: UpdateTagUseCase
) : BaseViewModel() {

    private val _tagStateData = MutableStateFlow<TagState>(TagState.CHOOSE)
    val tagStateData = _tagStateData.asStateFlow()
    private val _tagListData = mutableResultState<List<TagTable>>()
    val tagListData = _tagListData.asStateFlow()
    private val _checkedEditTagCnt = MutableStateFlow(0)
    val checkedEditTagCnt = _checkedEditTagCnt.asStateFlow()

    fun setTagState(tagState: TagState) {
        _tagStateData.value = tagState
    }

    fun setCheckedEditTagCnt(cnt:Int) {
        _checkedEditTagCnt.value = cnt
    }

    fun getTagList() {
        getTagListUseCase.invoke().onEach {
            _tagListData.value = ResultUiState.Success(it)
        }.catch {
            _tagListData.value = ResultUiState.Error(it)
        }.launchIn(CoroutineScope(ioDispatcher))
    }

    fun addTag(name: String) = CoroutineScope(ioDispatcher).launch {
        val tagTable = TagTable(name = name)
        addTagUseCase.invoke(tagTable)
    }

    fun deleteTag(idList: List<Int>) = CoroutineScope(ioDispatcher).launch {
        val deletedCnt = deleteTagUseCase.invoke(idList)
        setCheckedEditTagCnt(checkedEditTagCnt.value - deletedCnt)
    }

    fun updateTag(tagTable: TagTable) = CoroutineScope(ioDispatcher).launch {
        updateTagUseCase.invoke(tagTable)
    }

}