package com.lok.dev.coredata.usecase

import com.lok.dev.coredata.repository.HistoryDatabaseRepository
import javax.inject.Inject

class GetHistoryUseCase @Inject constructor(
    private val repository: HistoryDatabaseRepository
) {

    operator fun invoke(id: Int, cnt: Int) = repository.getHistoryTable(id, cnt)
}