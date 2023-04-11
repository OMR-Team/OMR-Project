package com.lok.dev.coredatabase.di

import android.content.Context
import androidx.room.Room
import com.lok.dev.coredatabase.dao.*
import com.lok.dev.coredatabase.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context : Context) : AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "OMRChecker.db")
            .setQueryCallback(
                { sqlQuery, _ -> /*Log.i("123123, "sqlQuery : $sqlQuery")*/ },
                Executors.newSingleThreadExecutor()
            )
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideOMRDao(appDatabase: AppDatabase) : OMRDao = appDatabase.getOMRDao()

    @Singleton
    @Provides
    fun provideTagDao(appDatabase: AppDatabase) : TagDao = appDatabase.getTagDao()

    @Singleton
    @Provides
    fun provideSubjectDao(appDatabase: AppDatabase) : SubjectDao = appDatabase.getSubjectDao()

    @Singleton
    @Provides
    fun provideProblemDao(appDatabase: AppDatabase) : ProblemDao = appDatabase.getProblemDao()

    @Singleton
    @Provides
    fun provideAnswerDao(appDatabase: AppDatabase) : AnswerDao = appDatabase.getAnswerDao()

    @Singleton
    @Provides
    fun provideResultDao(appDatabase: AppDatabase) : ResultDao = appDatabase.getResultDao()

    @Singleton
    @Provides
    fun provideHistoryDao(appDatabase: AppDatabase) : HistoryDao = appDatabase.getHistoryDao()

}