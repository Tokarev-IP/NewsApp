package test.app.newsapp.store

import test.app.newsapp.db.NewsData

sealed class NewsState {

    object Error : NewsState()
    object NoInternet : NewsState()
    data class Loading(val data: List<NewsData>) : NewsState()

}
