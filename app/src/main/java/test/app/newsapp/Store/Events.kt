package test.app.newsapp.Store

import test.app.newsapp.db.NewsData

sealed interface Events {

    sealed class Internal : Events {

        data class DataIsLoad(val data: List<NewsData>) : Internal()
        data class ErrorLoad(val error: Throwable) : Internal()
        data class NoInternet(val error: Throwable) : Internal()
        object PageUp : Internal()
    }
}