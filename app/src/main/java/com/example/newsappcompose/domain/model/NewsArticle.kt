package com.example.newsappcompose.domain.model

data class NewsArticle(
    val source:String,
    val author:String?,
    val title:String,
    val description:String?,
    val url:String,
    val urlToImage:String?,
    val publishedAt:String,
    val content:String?,
)
