package com.lok.dev.coredata.repository;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016J\u000e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006H\u0016J\u000e\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006H\u0016J\u000e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0006H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/lok/dev/coredata/repository/OMRDatabaseRepositoryImpl;", "Lcom/lok/dev/coredata/repository/OMRDatabaseRepository;", "omrDao", "Lcom/lok/dev/coredatabase/dao/OMRDao;", "(Lcom/lok/dev/coredatabase/dao/OMRDao;)V", "getAnswerTable", "Lkotlinx/coroutines/flow/Flow;", "Lcom/lok/dev/coredatabase/entity/AnswerTable;", "getHistoryTable", "Lcom/lok/dev/coredatabase/entity/HistoryTable;", "getOMRTable", "Lcom/lok/dev/coredatabase/entity/OMRTable;", "getTestTable", "Lcom/lok/dev/coredatabase/entity/TestTable;", "coreData_debug"})
public final class OMRDatabaseRepositoryImpl implements com.lok.dev.coredata.repository.OMRDatabaseRepository {
    private final com.lok.dev.coredatabase.dao.OMRDao omrDao = null;
    
    @javax.inject.Inject()
    public OMRDatabaseRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.lok.dev.coredatabase.dao.OMRDao omrDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public kotlinx.coroutines.flow.Flow<com.lok.dev.coredatabase.entity.OMRTable> getOMRTable() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public kotlinx.coroutines.flow.Flow<com.lok.dev.coredatabase.entity.TestTable> getTestTable() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public kotlinx.coroutines.flow.Flow<com.lok.dev.coredatabase.entity.AnswerTable> getAnswerTable() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public kotlinx.coroutines.flow.Flow<com.lok.dev.coredatabase.entity.HistoryTable> getHistoryTable() {
        return null;
    }
}