package rxjava

import io.reactivex.rxjava3.core.Single

interface TwoSinglesZipper {

    fun zipSources(): Single<String>

    class BaseImpl : TwoSinglesZipper {

        private var storage: String? = null

        private val one = Single.just("one")
        private val two = Single.just("two")

        override fun zipSources(): Single<String> = Single.zip(
            one,
            two
        ) { key, value ->
            applyZipResult(key, value)
        }.map {
            storage!!
        }

        private fun applyZipResult(key: String, value: String) {
            storage = "$key: $value"
        }
    }
}
