package test.app.newsapp

import android.app.Activity
import android.app.Application
import test.app.newsapp.di.DaggerNewsComponent
import test.app.newsapp.di.NewsComponent

class NewsApp: Application() {

    lateinit var appComponent: NewsComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerNewsComponent.factory().create(applicationContext)
    }

}

fun Activity.getComponent() = (application as NewsApp).appComponent
