package rxjava

import io.reactivex.rxjava3.core.Completable

class CompletableAndCompletable {

    private var dataStorage: String? = null

    fun operateData(data: String): Completable = pullData(data).andThen(defer())

    private fun pullData(data: String): Completable = Completable.fromCallable {
        throw NullPointerException("There is NULL!!!")
        dataStorage = data
        println("Pull completed")
    }
//        .doOnError {
//            println("log error: ${it.message}")
//        }
//        .onErrorResumeNext {
//            println("ResumeNext: ${it.message}")
//            Completable.complete()
//        }
        .onErrorResumeNext {
            println("ResumeNext: ${it.message}")
            Completable.error(it)
        }

    private fun calculateData(): Completable = Completable.fromCallable {
//        throw NullPointerException("There is NULL!!!")
        dataStorage = "calculate $dataStorage"
        println("calculate completed")
    }
        .doOnError {
            println("log error: ${it.message}")
        }
//        .onErrorResumeNext {
//            println("ResumeNext: ${it.message}")
//            Completable.complete()
//        }
//        .onErrorResumeNext {
//            println("ResumeNext: ${it.message}")
//            Completable.error(it)
//        }

    private fun defer() = Completable.defer { calculateData() }
}
