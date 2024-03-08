package rxjava

import io.reactivex.rxjava3.core.Single

class SingleDoOnSuccessSingle {

    private val alfa = Single.just("alfa")
    private val beta = Single.just("beta")

//    fun doWorkBad(): Single<String> {
//        return alfa.doOnSuccess {
//            val betaResult = beta.blockingGet()
//            println(betaResult)
//        }
//    }

    fun doWork(): Single<String> {
        return alfa.flatMap {
            beta
        }
    }
}
