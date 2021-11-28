package test.app.newsapp.api

import retrofit2.http.GET
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Query

interface NewsInterface {

    @GET("http://api.mediastack.com/v1/news")
    fun getNewsFromApi(
        @Query("access_key") key: String,
        @Query("countries") country: String,
        @Query("languages") language: String,
        @Query("offset") page: Int,
    ): Single<NewsDataResponse>
}