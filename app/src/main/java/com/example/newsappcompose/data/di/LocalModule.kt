package com.example.newsappcompose.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsappcompose.data.local.NewsDao
import com.example.newsappcompose.data.local.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideDatabaseInstance(@ApplicationContext context:Context):NewsDatabase{
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "News Database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(newsDatabase: NewsDatabase):NewsDao{
        return newsDatabase.newsDao
    }
}