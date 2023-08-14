package com.example.newsappcompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsappcompose.domain.model.NewsArticle

@Database(entities = [NewsArticle::class], version = 1, exportSchema = false)
abstract class NewsDatabase:RoomDatabase() {
    abstract val newsDao:NewsDao
}