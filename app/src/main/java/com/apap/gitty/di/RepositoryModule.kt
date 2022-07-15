package com.apap.gitty.di

import com.apap.gitty.data.repository.GithubRepository
import com.apap.gitty.data.repository.GithubRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun fleetRepository(impl: GithubRepositoryImpl): GithubRepository
}