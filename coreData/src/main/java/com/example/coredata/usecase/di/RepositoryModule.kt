package com.example.coredata.usecase.di

import com.example.coredata.usecase.repository.OMRDatabaseRepository
import com.example.coredata.usecase.repository.OMRDatabaseRepositoryImpl
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