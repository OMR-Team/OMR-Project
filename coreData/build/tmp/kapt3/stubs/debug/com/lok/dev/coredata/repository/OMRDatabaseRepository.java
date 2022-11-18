package com.lok.dev.coredata.repository;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H&J\u000e\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003H&J\u000e\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003H&J\u000e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0003H&\u00a8\u0006\u000b"}, d2 = {"Lcom/lok/dev/coredata/repository/OMRDatabaseRepository;", "", "getAnswerTable", "Lkotlinx/coroutines/flow/Flow;", "Lcom/lok/dev/coredatabase/entity/AnswerTable;", "getHistoryTable", "Lcom/lok/dev/coredatabase/entity/HistoryTable;", "getOMRTable", "Lcom/lok/dev/coredatabase/entity/OMRTable;", "getTestTable", "Lcom/lok/dev/coredatabase/entity/TestTable;", "coreData_debug"})
public abstract interface OMRDatabaseRepository {
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.lok.dev.coredatabase.entity.OMRTable> getOMRTable();
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.lok.dev.coredatabase.entity.TestTable> getTestTable();
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.lok.dev.coredatabase.entity.AnswerTable> getAnswerTable();
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.lok.dev.coredatabase.entity.HistoryTable> getHistoryTable();
}