package rxjava

import io.reactivex.rxjava3.core.Single

class SingleAndSingleAgain {

    private var count = 0

    private val asyncSource = Single.just("Hard Source")
        .doOnEvent { t1, t2 ->
        println("doOnEvent ${++count}")
    }.cache()

    private val share = asyncSource.toFlowable().singleOrError()

    private fun getKey(): Single<String> = asyncSource.map {
        println(".map: $it")
        "key from the $it |"
    }

    private fun asyncWork(): Single<String> = asyncSource
        .flatMap {
            println("flatMap: $it")
            Single.just("Work + $it /")
        }

    fun getResult(): Single<String> =
        Single.zip(
           asyncWork(),
           getKey()
        ) { work, key ->
            "Save = $work with $key "
        }
}
