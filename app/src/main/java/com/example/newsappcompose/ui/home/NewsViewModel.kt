package com.example.newsappcompose.ui.home

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsappcompose.MainActivity
import com.example.newsappcompose.NewsApplication
import com.example.newsappcompose.data.remote.State
import com.example.newsappcompose.data.repository.NewsRepository
import com.example.newsappcompose.domain.model.NewsArticle
import com.example.newsappcompose.utils.Internet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val applicationContext: Application
) : AndroidViewModel(applicationContext) {
    private val TAG = "NewsViewModel"

    private var newsViewModelJob:Job? = null
    private var viewModelScope:CoroutineScope? = null

    val newsArticles = mutableStateOf<State<List<NewsArticle>>>(State.Empty())

    fun getNewsArticles() {
        newsViewModelJob?.cancel()
        newsViewModelJob=Job()
        viewModelScope= CoroutineScope(Dispatchers.IO +newsViewModelJob!!)

        viewModelScope?.launch {
            if (Internet.isInternetConnected(applicationContext)) {
                newsArticles.value=State.Loading()
                val responseResult = newsRepository.getNewsArticles("us")
                when (responseResult) {
                    is State.Success -> newsArticles.value=State.Success(responseResult.data!!)
                    is State.Error -> {
                        newsArticles.value=State.Error("There is an error occurred")
                        Log.e(TAG, "getNewsArticles: ${responseResult.message.toString()}")
                    }
                    else->{}
                }
            } else newsArticles.value=State.Error("No Internet Connection")
        }
    }

    fun cancelLoading(){
        newsViewModelJob?.cancel()
        newsArticles.value=State.Empty()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope?.cancel()
    }
}