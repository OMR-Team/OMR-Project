package com.lok.dev.coredata.di

import com.lok.dev.coredata.repository.OMRDatabaseRepository
import com.lok.dev.coredata.repository.OMRDatabaseRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindsOMRDatabaseRepository(
        omrDatabaseRepository : OMRDatabaseRepositoryImpl
    ) : OMRDatabaseRepository

}