package test.app.newsapp.Store

import test.app.newsapp.api.NewsDataResponse
import test.app.newsapp.db.NewsData

sealed class Command {

    data class LoadNewsData(val page: Int) : Command()

    data class DataIsLoad(val data: NewsDataResponse) : Command()

    object DataIsSetIntoRoom : Command()

    object PageUp : Command()

    data class DataFromRoom(val data: List<NewsData>) : Command()

    data class LoadError(val error: Throwable) : Command()

    data class NoInternet(val error: Throwable) : Command()

    data class DeleteData(val data: NewsDataResponse) : Command()
}
