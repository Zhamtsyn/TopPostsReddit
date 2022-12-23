package com.example.toppostsreddit.di

import com.example.toppostsreddit.TopPostsRepository
import com.example.toppostsreddit.data.TopPostsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideCurrencyApi(): TopPostsApi = Retrofit.Builder()
        .baseUrl("https://www.reddit.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TopPostsApi::class.java)

    @Singleton
    @Provides
    fun provideConverterRepository(api:TopPostsApi): TopPostsRepository = TopPostsRepository(api)
}