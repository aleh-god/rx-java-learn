package rxjava

import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

class MaybeToSingle {

    private val dataStorage: Map<Int, String> = mapOf(
        1 to "one",
        2 to "two",
        3 to "three"
    )

    fun getDataBy(key: Int): Single<String> = maybeDataBy(key)
        .toSingle()
        .onErrorResumeNext {
        Single.just("null")
    }

    private fun maybeDataBy(key: Int): Maybe<String> = Maybe.create {
        dataStorage[key]?.let { data ->
            it.onSuccess(data)
        } ?: it.onComplete()
    }
}

/*
        .flatMap(
            Function {
                Single.just(it) // onSuccess
            },
            Function {
                Single.error(it) // onError
            },
            Callable {
                feed.getSomething() // onComplete
            }
        )
 */