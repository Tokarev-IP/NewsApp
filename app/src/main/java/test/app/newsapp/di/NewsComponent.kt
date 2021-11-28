package test.app.newsapp.di

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import test.app.newsapp.MainActivity
import test.app.newsapp.api.ApiClientModule
import test.app.newsapp.db.DbModule
import test.app.newsapp.di.viewmodel.ViewModelModule
import test.app.newsapp.fragments.newsline.NewsLineFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ViewModelModule::class,
        ApiClientModule::class,
        DbModule::class,
    ]
)
interface NewsComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): NewsComponent
    }

    fun inject(application: Application)
    fun inject(fragment: NewsLineFragment)
}