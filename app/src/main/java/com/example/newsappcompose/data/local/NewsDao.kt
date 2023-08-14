package com.example.newsappcompose.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsappcompose.domain.model.NewsArticle

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(newsList:List<NewsArticle>)

    @Query("Select * from news_table")
    suspend fun getNews():List<NewsArticle>

}