package com.example.newsappcompose.data.repository

import android.util.Log
import com.example.newsappcompose.data.remote.NewsService
import com.example.newsappcompose.data.remote.State
import com.example.newsappcompose.domain.model.toNewsArticles
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsService: NewsService) {

    private val TAG = "NewsRepository"

    suspend fun getNewsArticles(country: String) = withContext(Dispatchers.IO) {
        try {
            val responseResult = newsService.getNewsArticles(country)
            if (responseResult.isSuccessful) {
                State.Success(responseResult.body()?.toNewsArticles())
            } else State.Error(responseResult.message())
        } catch (e: Exception) {
            Log.e(TAG, "getNewsArticles: ${e.message}")
            State.Error(e.message.toString())
        }
    }
}