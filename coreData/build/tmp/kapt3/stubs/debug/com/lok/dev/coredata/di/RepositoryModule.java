package com.lok.dev.coredata.di;

import java.lang.System;

@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\'\u00a8\u0006\u0006"}, d2 = {"Lcom/lok/dev/coredata/di/RepositoryModule;", "", "bindsOMRDatabaseRepository", "Lcom/lok/dev/coredata/repository/OMRDatabaseRepository;", "omrDatabaseRepository", "Lcom/lok/dev/coredata/repository/OMRDatabaseRepositoryImpl;", "coreData_debug"})
@dagger.Module()
public abstract interface RepositoryModule {
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Binds()
    public abstract com.lok.dev.coredata.repository.OMRDatabaseRepository bindsOMRDatabaseRepository(@org.jetbrains.annotations.NotNull()
    com.lok.dev.coredata.repository.OMRDatabaseRepositoryImpl omrDatabaseRepository);
}