package com.apap.gitty.di

import com.apap.gitty.domain.LocalDateTimeJsonAdapter
import com.squareup.moshi.Moshi
import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.time.Duration

@Module
@InstallIn(SingletonComponent::class)
object BackendModule {

    private const val baseUrl = "https://api.github.com/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .readTimeout(Duration.ofSeconds(10))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    fun moshi(): Moshi = Moshi.Builder()
        .add(LocalDateTimeJsonAdapter())
        .build()
}