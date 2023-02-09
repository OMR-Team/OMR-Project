package com.lok.dev.omrchecker.di

import com.lok.dev.omrchecker.setting.AddSubjectDialog
import com.lok.dev.omrchecker.setting.SubjectDialog
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ActivityComponent::class)
class DialogFragmentModule {

    @Provides
    fun providesSubjectDialog(): SubjectDialog = SubjectDialog()

    @Provides
    fun providesAddSubjectDialog(): AddSubjectDialog = AddSubjectDialog()

}