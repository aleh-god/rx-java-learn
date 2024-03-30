package coroutines

import kotlinx.coroutines.*

private suspend fun loadDataFromApi(): String {
    delay(2000)
    return "Remote data in ${Thread.currentThread()} at ${System.currentTimeMillis()}"
}

class AwaitSharing() {

    private val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
    }

    private val dataLayerCoroutineScope: CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default + handler)

    private var apiCall: Deferred<String>? = null

    private suspend fun getData(): String = handleDeferred().await()

    private fun handleDeferred(): Deferred<String> =
        apiCall ?: dataLayerCoroutineScope
            .async { loadDataFromApi() }
            .also { apiCall = it }

    fun executeWorks() = runBlocking {
        println("start runBlocking in ${Thread.currentThread()}")
        coroutineScope {
            println("start coroutineScope in ${Thread.currentThread()}")
            launch {
                println("start launch-1 in ${Thread.currentThread()}")
                val result = getData()
                println("stop launch-1 with result $result")
            }
            launch {
                delay(500)
                println("start launch-2 in ${Thread.currentThread()}")
                val result = getData()
                println("stop launch-2 with result $result")
            }
            launch {
                delay(1000)
                println("start launch-3 in ${Thread.currentThread()}")
                val result = getData()
                println("stop launch-3 with result $result")
            }

            println("stop coroutineScope")
        }
        println("stop runBlocking")
    }
}

