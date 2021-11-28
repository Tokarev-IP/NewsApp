package test.app.newsapp.db

import android.util.Log
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import test.app.newsapp.api.NewsDataResponse
import java.util.*
import javax.inject.Inject

class DbRepository @Inject constructor(
    private val db: NewsDataBase
) {

    fun setDataRoom(news: NewsDataResponse): Completable {
        return Completable.create {
            for (item in news.data) {
                Log.d("INSERT", item.toString())
                db.citiesDao().insertDataToRoom(
                    NewsData(
                        UUID.randomUUID().toString(),
                        item.title,
                        item.description,
                        item.image
                    )
                )
                    .subscribeOn(Schedulers.io())
                    .subscribe({

                    }, { error ->
                        Log.e("ROOM", error.toString())
                    })
            }
            it.onComplete()
        }
    }

    fun getDataRoom(): Single<List<NewsData>> {
        return Single.create {
            db.citiesDao().getDataFromRoom()
                .subscribeOn(Schedulers.io())
                .subscribe({ data ->
                    it.onSuccess(data)
                }, { error ->
                    it.onError(error)
                })
        }
    }

    fun deleteDataRoom(): Completable {
        return Completable.create {
            db.citiesDao().deleteNews()
                .subscribeOn(Schedulers.io())
                .subscribe({
                    it.onComplete()
                }, { error ->
                    it.onError(error)
                })
        }
    }
}