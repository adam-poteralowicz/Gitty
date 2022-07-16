package com.apap.gitty.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object SharedPreferencesModule {

    @Provides
    @Singleton
    fun sharedPreferences(application: Application): SharedPreferences =
        application.getSharedPreferences("preferences", Context.MODE_PRIVATE)
}