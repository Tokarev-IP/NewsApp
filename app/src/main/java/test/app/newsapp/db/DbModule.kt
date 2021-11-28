package test.app.newsapp.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DbModule {

    @Singleton
    @Provides
    fun getDatabase(context: Context): NewsDataBase {
        return Room.databaseBuilder(
            context.applicationContext,
            NewsDataBase::class.java,
            "news_database"
        ).build()
    }
}