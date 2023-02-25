package com.lok.dev.omrchecker.di

import com.lok.dev.omrchecker.setting.subject.SubjectDialog
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class DialogFragmentModule {

    @Provides
    fun providesSubjectDialog(): SubjectDialog = SubjectDialog()

}