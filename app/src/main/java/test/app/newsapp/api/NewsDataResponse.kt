package test.app.newsapp.api

import test.app.newsapp.db.NewsData

data class NewsDataResponse(

    val data: List<NewsData>
)
