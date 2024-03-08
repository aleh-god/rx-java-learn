package rxjava

import common.MyException
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

class OnErrorNextAndHandling {

    fun doWork(): Flowable<String> {
        return Single.just(true)
            .toFlowable()
            .flatMap {
                if (it) {
                    Flowable.fromCallable {
                        throw MyException()
                        "Work with $it"
                    }
                        .doOnError {
                            println("Log.error: ${it.message}")
                            "Work is stopped"
                        }

                }
                else Flowable.never<String>()
            }
    }
}