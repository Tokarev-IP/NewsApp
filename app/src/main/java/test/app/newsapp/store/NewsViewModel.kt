package test.app.newsapp.store

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.schedulers.Schedulers
import test.app.newsapp.db.NewsData
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val actor: Actor
) : ViewModel() {

    init {
        actor.getActorSubject()
            .subscribeOn(Schedulers.io())
            .subscribe({ event ->
                result(event)
            },{})
    }

    private val data: MutableLiveData<NewsState> = MutableLiveData()
    private var page: Int = 0

    private fun result(events: Events){

        when (events){
            is Events.Internal.DataIsLoad -> {
                setData(events.data)
            }

            is Events.Internal.ErrorLoad -> {
                setErrorData()
            }

            is Events.Internal.NoInternet -> {
                setNoInternetData()
            }

            is Events.Internal.PageUp -> {
                pageUp()
            }
        }
    }

    fun getData() = data

    fun getNewsFromApi() {
        actor.execute(Command.LoadNewsData(page))
    }

    private fun setData(news: List<NewsData>){
        data.postValue(NewsState.Loading(news))
    }

    private fun setErrorData(){
        data.postValue(NewsState.Error)
    }

    private fun setNoInternetData(){
        data.postValue(NewsState.NoInternet)
    }

    private fun pageUp(){
        page += NEWS_COUNT
    }

    companion object{
        private const val NEWS_COUNT = 26
    }

}