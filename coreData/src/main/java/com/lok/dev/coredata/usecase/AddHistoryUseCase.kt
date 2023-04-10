package com.lok.dev.coredata.usecase

import com.lok.dev.coredata.repository.HistoryDatabaseRepository
import com.lok.dev.coredatabase.entity.HistoryTable
import javax.inject.Inject

class AddHistoryUseCase @Inject constructor(
    private val repository: HistoryDatabaseRepository
) {
    operator fun invoke(historyTable: HistoryTable) = repository.addHistoryTable(historyTable)
}