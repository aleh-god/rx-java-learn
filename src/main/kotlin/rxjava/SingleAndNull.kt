package rxjava

import io.reactivex.rxjava3.core.Single

interface SingleAndNull {

    fun getNullableData(key: Int): Single<String>

    class BaseImpl : SingleAndNull {

        private val dataStorage: Map<Int, String> = mapOf(
            1 to "one",
            2 to "two",
            3 to "three"
        )

        private fun getDataBy(key: Int): Single<String> = Single.fromCallable {
            dataStorage[key]!!
        }

        override fun getNullableData(key: Int): Single<String> = getDataBy(key).onErrorResumeNext {
            Single.just("Something different")
        }
    }
}
