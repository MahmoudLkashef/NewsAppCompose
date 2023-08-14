package com.example.newsappcompose.data.repository

import android.util.Log
import com.example.newsappcompose.data.local.NewsDao
import com.example.newsappcompose.data.remote.NewsService
import com.example.newsappcompose.data.remote.State
import com.example.newsappcompose.domain.model.NewsArticle
import com.example.newsappcompose.domain.model.toNewsArticles
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsService: NewsService,private val newsDao: NewsDao) {

    private val TAG = "NewsRepository"

    suspend fun getNewsArticles(country: String) = withContext(Dispatchers.IO) {
        try {
            val responseResult = newsService.getNewsArticles(country)
            if (responseResult.isSuccessful) {
                responseResult.body()?.let { newsDao.insertNews(it.toNewsArticles())}
                State.Success(newsDao.getNews())
            } else State.Error(responseResult.message())
        } catch (e: Exception) {
            Log.e(TAG, "getNewsArticles: ${e.message}")
            State.Error(e.message.toString())
        }
    }

    suspend fun getCachedNewsArticles():List<NewsArticle>{
        return newsDao.getNews()
    }
}