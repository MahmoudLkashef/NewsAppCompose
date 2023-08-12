package com.example.newsappcompose.ui.home

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.newsappcompose.R
import com.example.newsappcompose.data.remote.State
import com.example.newsappcompose.domain.model.NewsArticle
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState


@Preview()
@Composable
fun preview() {
    //NewsArticleListItem()
    NewsHomeScreen()
}


@Composable
fun NewsHomeScreen() {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.onSecondaryContainer

    ) {

        val newsViewModel = hiltViewModel<NewsViewModel>()

        Column {
            NewsHomeHeader(
                onLoadNewsClicked = { newsViewModel.getNewsArticles() },
                onCancelClicked = {newsViewModel.cancelLoading()}
            )

            when (val state = newsViewModel.newsArticles.value) {
                is State.Success -> {
                    val newsArticles = state.data!!
                    LazyColumn {
                        items(newsArticles) { newsArticle ->
                            NewsArticleListItem(newsArticle)
                        }
                    }
                }

                is State.Error -> {
                    Toast.makeText(LocalContext.current, state.message, Toast.LENGTH_SHORT).show()
                }

                is State.Loading -> {
                    Box(modifier = Modifier.align(Alignment.CenterHorizontally)){
                        CircularProgressIndicator()
                    }
                }

                is State.Empty->{}
            }
        }

    }


}


@Composable
fun NewsHomeHeader(onLoadNewsClicked: () -> Unit, onCancelClicked: () -> Unit) {
    Column {
        Text(
            text = "Newsly",
            fontFamily = FontFamily(Font(R.font.lexend_zetta)),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(modifier = Modifier
                .weight(1f)
                .padding(5.dp),
                shape = RoundedCornerShape(8.dp),
                onClick = { onLoadNewsClicked() }) {
                Text(text = "Load News")
            }
            Button(modifier = Modifier
                .weight(1f)
                .padding(5.dp),
                shape = RoundedCornerShape(8.dp),
                onClick = { onCancelClicked() }) {
                Text(text = "Cancel")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun NewsArticleListItem(newsArticle: NewsArticle) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(4.dp)
        , shape = RoundedCornerShape(16.dp)

    ) {
        Row(Modifier.padding(end = 2.dp)) {
            newsArticle.urlToImage?.let { NewsIcon(it) }
            Spacer(modifier = Modifier.width(4.dp))
            NewsTitles(newsArticle)
        }
    }
}

@Composable
fun NewsIcon(imageUrl: String) {
    Box(
        modifier = Modifier
            .width(150.dp)
            .fillMaxHeight()
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .error(R.drawable.error)
                .build()
        )

        if (painter.state is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator()
        }

        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(CornerSize(16.dp)))
                .fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }
}

@Composable
fun NewsTitles(newsArticle: NewsArticle) {
    Column {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Source: ${newsArticle.source}", color = Color.Black)
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = newsArticle.title,
            fontSize = 18.sp,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.chau_philomene_one))
        )
        Spacer(modifier = Modifier.height(5.dp))
        Row {
            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = "",
                modifier = Modifier.padding(end = 5.dp)
            )
            Text(
                text = newsArticle.publishedAt,
                modifier = Modifier.padding(top = 2.dp)
            )

        }
    }
}

/*
@Composable
fun ArticleWebView(url:String){
    val state= rememberWebViewState(url = url)
    Column(modifier = Modifier.fillMaxSize()) {

        WebView(
            state = state,
            modifier = Modifier.fillMaxSize(),
            onCreated = {
                it.settings.javaScriptEnabled
                it.settings.domStorageEnabled=true
                it.settings.allowContentAccess=true
            }
        )
    }
}*/
