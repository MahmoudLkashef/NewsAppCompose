package com.example.newsappcompose.data.remote

import com.example.newsappcompose.domain.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("top-headlines")
    suspend fun getNewsArticles(@Query("country") country: String):Response<NewsResponse>
}
