package test.app.newsapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface NewsDao {

    @Query("SELECT * FROM news_db")
    fun getDataFromRoom(): Single<List<NewsData>>

    @Insert
    fun insertDataToRoom(news: NewsData): Completable

    @Query("DELETE FROM news_db")
    fun deleteNews(): Completable
}