package com.example.toppostsreddit.di

import android.app.Application
import androidx.room.Room
import com.example.toppostsreddit.data.TopPostsApi
import com.example.toppostsreddit.data.database.PostsDatabase
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
    fun provideDatabase(app:Application):PostsDatabase=
        Room.databaseBuilder(app, PostsDatabase::class.java, "posts_database")
            .fallbackToDestructiveMigration().build()
}