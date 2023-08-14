package com.example.newsappcompose.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_table")
data class NewsArticle(
    @PrimaryKey(autoGenerate = true)
    var id:Long=0,
    val source:String,
    val author:String?,
    val title:String,
    val description:String?,
    val url:String,
    val urlToImage:String?,
    val publishedAt:String,
    val content:String?,
)
