package test.app.newsapp.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        NewsData::class
    ],
    version = 1
)
abstract class NewsDataBase : RoomDatabase() {

    abstract fun citiesDao(): NewsDao

}
