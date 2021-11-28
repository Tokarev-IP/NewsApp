package test.app.newsapp.Store

import android.util.Log
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import test.app.newsapp.api.ApiRepository
import test.app.newsapp.api.NewsDataResponse
import test.app.newsapp.db.DbRepository
import javax.inject.Inject

class Actor @Inject constructor(
    private val apiRep: ApiRepository,
    private val dbRep: DbRepository,
) {

    private val actorSubject = PublishSubject.create<Events>()

    fun getActorSubject(): PublishSubject<Events> = actorSubject

    fun execute(command: Command) {

        when (command) {

            is Command.LoadNewsData -> {
                getNewsApi(command.page)
            }

            is Command.DataIsLoad -> {
                setNewsToRoom(command.data)
            }

            is Command.DataIsSetIntoRoom -> {
                getNewsFromRoom()
            }

            is Command.LoadError -> {
                actorSubject.onNext(Events.Internal.ErrorLoad(command.error))
            }

            is Command.DataFromRoom -> {
                actorSubject.onNext(Events.Internal.DataIsLoad(command.data))
            }

            is Command.DeleteData -> {
                deleteDataFrom(command.data)
            }

            is Command.NoInternet -> {
                actorSubject.onNext(Events.Internal.NoInternet(command.error))
            }

            is Command.PageUp -> {
                actorSubject.onNext(Events.Internal.PageUp)
            }
        }

    }

    private fun getNewsApi(page: Int) {
        apiRep.getDataFromApi(page)
            .subscribeOn(Schedulers.io())
            .subscribe({ data ->
                execute(Command.PageUp)
                if (page == FIRST_LOAD)
                    execute(Command.DeleteData(data))
                else
                    execute(Command.DataIsLoad(data))
            }, { error ->
                execute(Command.NoInternet(error))
                execute(Command.DataIsSetIntoRoom)
            })
    }

    private fun setNewsToRoom(data: NewsDataResponse) {
        dbRep.setDataRoom(data)
            .subscribeOn(Schedulers.io())
            .subscribe({
                execute(Command.DataIsSetIntoRoom)
            }, { error ->
                execute(Command.LoadError(error))
            })
    }

    private fun getNewsFromRoom() {
        dbRep.getDataRoom()
            .subscribeOn(Schedulers.io())
            .subscribe({ data ->
                if (data.isEmpty())
                    execute(Command.LoadError(Throwable()))
                else
                    execute(Command.DataFromRoom(data))
            }, {

            })
    }

    private fun deleteDataFrom(data: NewsDataResponse) {
        dbRep.deleteDataRoom()
            .subscribeOn(Schedulers.io())
            .subscribe({
                execute(Command.DataIsLoad(data))
                Log.d("API", " data has been deleted")
            }, { error ->
                Log.d("API", error.toString())
            })
    }

    companion object{
        private const val FIRST_LOAD = 0
    }
}