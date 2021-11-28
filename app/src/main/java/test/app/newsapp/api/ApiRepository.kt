package test.app.newsapp.api

import android.util.Log
import javax.inject.Inject
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.core.Single

class ApiRepository @Inject constructor(
    private val apiClient: NewsInterface,
) {

    fun getDataFromApi(page: Int): Single<NewsDataResponse> {
        Log.d("PAGE", page.toString())
        return Single.create<NewsDataResponse> {
            apiClient.getNewsFromApi(
                "b4819d9777837a43bf3e8fb23757c3af",
                "ru",
                "ru",
                page
            )
                .subscribeOn(Schedulers.io())
                .subscribe({ data ->
                    it.onSuccess(data)
                    Log.d("API_DATA", data.toString())
                }, { error ->
                    Log.e("API", error.toString())
                    it.onError(error)
                })
        }
    }
}