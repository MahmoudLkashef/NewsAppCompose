package com.example.newsappcompose.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*object RetrofitInstance {

    private const val BASE_URL = "https://newsapi.org/v2/"

    private val retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val url = chain
                            .request()
                            .url()
                            .newBuilder()
                            .addQueryParameter("apiKey", API_KEY)
                            .build()
                        chain.proceed(chain.request().newBuilder().url(url).build())
                    }.build()
            )
            .build()
    }

    val newsService: NewsService by lazy {
        retrofit.create(NewsService::class.java)
    }

}*/
