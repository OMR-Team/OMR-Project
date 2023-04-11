package com.lok.dev.coredata.usecase

import com.lok.dev.coredata.repository.ResultDatabaseRepository
import javax.inject.Inject

class GetResultJoinUseCase @Inject constructor(
    private val repository: ResultDatabaseRepository
){
    operator fun invoke(id: Int, no: Int) = repository.getResultJoinTable(id, no)
}