package rxjava

import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.FlowableOnSubscribe
import io.reactivex.rxjava3.core.Observable

class ObservableAndFlowable {

    val observable: Observable<Int> = Observable.create {
        it.onNext(1)
        Thread.sleep(1000)
        it.onNext(2)
        it.onComplete()
    }

    private val source: FlowableOnSubscribe<Int> = FlowableOnSubscribe {
        it.onNext(1)
        Thread.sleep(1000)
        it.onNext(2)
        it.onComplete()
    }

    val flowable: Flowable<Int> = Flowable.create(source, BackpressureStrategy.BUFFER)
}
