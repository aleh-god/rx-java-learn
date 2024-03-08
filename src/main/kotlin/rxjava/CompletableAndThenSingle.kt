package rxjava

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class CompletableAndThenSingle {

    private var dataStorage: String? = null

    fun reloadData(data: String): Single<String> = pullData(data).andThen(defer())

    private fun pullData(data: String): Completable = Completable.fromCallable {
        dataStorage = data
        println("Pull completed")
    }

    private fun getData(): Single<String> = Single.fromCallable {
        dataStorage ?: "Empty"
    }

    private fun defer() = Single.defer {
        getData()
    }
}
