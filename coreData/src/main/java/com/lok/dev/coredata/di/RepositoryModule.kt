package com.lok.dev.coredata.di

import com.lok.dev.coredata.repository.*
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

    @Binds
    fun bindsTagDatabaseRepository(
        tagDatabaseRepository : TagDatabaseRepositoryImpl
    ) : TagDatabaseRepository

    @Binds
    fun bindsSubjectDatabaseRepository(
        subjectDatabaseRepository: SubjectDatabaseRepositoryImpl
    ) : SubjectDatabaseRepository
}