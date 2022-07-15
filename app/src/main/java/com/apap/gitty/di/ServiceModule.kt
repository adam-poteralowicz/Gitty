package com.apap.gitty.di

import com.apap.gitty.data.service.GithubService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    fun fleetService(retrofit: Retrofit): GithubService = retrofit.create()
}