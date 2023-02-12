package com.lok.dev.commonutil.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainImmediateDispatcher

@Module
@InstallIn(SingletonComponent::class)
class DispatcherModule {

    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher() = Dispatchers.Default

    @IoDispatcher
    @Provides
    fun providesIoDispatcher() = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun providesMainDispatcher() = Dispatchers.Main

    @MainImmediateDispatcher
    @Provides
    fun providesMainImmediateDispatcher() = Dispatchers.Main.immediate

}