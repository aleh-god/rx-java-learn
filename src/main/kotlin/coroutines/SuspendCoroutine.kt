package coroutines

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class SuspendCoroutine {

    fun executeDelay() = runBlocking {
        println("start runBlocking in ${Thread.currentThread()}")
        this.launch {
            sleep()
            println("stop continuation in ${Thread.currentThread()}")
        }
        println("stop runBlocking")
    }

    private suspend fun sleep() {
        suspendCoroutine<Unit> { continuation ->
            println("start continuation in ${Thread.currentThread()}")
            thread {
                println("start sleep in ${Thread.currentThread()}")
                Thread.sleep(1000)
                println("end sleep in ${Thread.currentThread()}")
                continuation.resume(Unit)
            }
        }
    }
}
