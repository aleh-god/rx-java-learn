package rxjava

import common.MyException
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

interface FlowableToSingle {

    fun getResult(): Single<String>

    class BaseImpl() : FlowableToSingle {
        override fun getResult(): Single<String> {
            val countFlowAlfa = Observable.create<Int> {
                (1..3).forEach { value ->
                    println("Thread.sleep Alfa: $value")
                    Thread.sleep(1000)
                    it.onNext(value)
                }
                it.onComplete()
            }
                .subscribeOn(Schedulers.io())
                .toFlowable(BackpressureStrategy.BUFFER)

            val countFlowBeta = Observable.create<Int> {
                try {
                    (1..5).forEach { value ->
                        if (value == 3) it.onError(IllegalStateException("is stopped"))
//                        if (value == 3) it.onError(MyException())
                        println("Thread.sleep Beta: $value")
                        Thread.sleep(400)
                        it.onNext(value)
                    }
                    it.onComplete()
                } catch (e: Exception) {
                    it.onError(e)
                }
            }
                .subscribeOn(Schedulers.io())
                .toFlowable(BackpressureStrategy.BUFFER)

            return Flowable.combineLatest(
                countFlowAlfa,
                countFlowBeta
            ) { p1: Int, p2: Int ->
                println("combineLatest: $p1 * $p2")
                p1 * p2
            }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnSubscribe {
                    println("doOnSubscribe: 0")
                }
                .onErrorResumeNext { throwable: Throwable ->
                    println("onErrorResumeNext: ${throwable.message}")
                    if (throwable is MyException) Flowable.just(-1)
                    else Flowable.error(throwable)
                }
                .doOnNext {
                    println("doOnNext: $it")
                }
                .doOnComplete {
                    println("doOnComplete: 100")
                }
                .lastOrError()
                .doOnError {
                    println("doOnError: ${it.message}")
                }
                .map {
                    if (it == -1) "Fail"
                    else "Success"
                }
        }
    }
}
