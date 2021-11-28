package test.app.newsapp.api

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object ApiClientModule {

    @Singleton
    @Provides
    fun getApiClient(): NewsInterface {
        return Retrofit.Builder()
            .baseUrl("https://api.mediastack.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(NewsInterface::class.java)
    }
}