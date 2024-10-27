package coroutines

import common.MyException
import kotlinx.coroutines.*

class CoroutineException {

    private suspend fun loadDataAsync(): String {
        println("start data loading")
        delay(1000)
        throw MyException()

        println("stop data loading")
        return "data"
    }

    fun handleSuspendException() = runBlocking {
        println("start runBlocking")

        try {
            println("try")
            loadDataAsync()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Throwable) {
            println("catch: ${e.message}")
        } finally {
            withContext(NonCancellable) {
                println("finally")
            }
        }

        println("stop runBlocking")
    }

    fun handleLaunchException() = runBlocking {
        println("start runBlocking")

        try {
            println("try")
            // TODO("DON'T USE")
            // launch {
            coroutineScope {
                delay(100)
                println("launch")
                loadDataAsync()
            }
        } catch (e: Throwable) {
            println("catch: ${e.message}")
        } finally {
            println("finally")
        }

        println("stop runBlocking")
    }

    fun handleAwaitException() = runBlocking {
        println("start runBlocking")

        val task = async {
            try {
                loadDataAsync()
            } catch (e: CancellationException) {
                println("re-throw: ${e.message}")
                throw e
            } catch (e: Throwable) {
                println("catch: ${e.message}")
                e.message ?: "null"
            } finally {
                println("finally")
            }
        }

        val result = task.await()
        println("Result: $result")

        println("stop runBlocking")
    }

    fun scopeHandleAwaitException() = runBlocking {
        println("start runBlocking")

        val result = try {
            coroutineScope {
                val taskAlfa = async { loadDataAsync() }
                val taskBeta = async { loadDataAsync() }
                return@coroutineScope taskAlfa.await() + taskBeta.await()
            }
        } catch (e: Throwable) {
            println("catch: ${e.message}")
            e.message ?: "null"
        } finally {
            println("finally")
        }
        println("Result: $result")

        println("stop runBlocking")
    }

    fun superHandleAwaitException() = runBlocking {
        println("start runBlocking")

        // TODO("DON'T USE")
        // coroutineScope {
        supervisorScope {
            val task = async { loadDataAsync() }
            try {
                val result = task.await()
                println("Result: $result")
            } catch (e: Throwable) {
                println("catch: ${e.message}")
                e.message ?: "null"
            } finally {
                println("finally")
            }
        }

        println("stop runBlocking")
    }

    fun jobHandleAwaitException() = runBlocking {
        println("start runBlocking")

        // or SupervisorJob()
        // it is a structured concurrency violation
        val task = async(Job()) { loadDataAsync() }
        try {
            val result = task.await()
            println("Result: $result")
        } catch (e: Throwable) {
            println("catch: ${e.message}")
            e.message ?: "null"
        } finally {
            println("finally")
        }

        println("stop runBlocking")
    }

    fun lazyHandleLaunchException() = runBlocking {
        println("start runBlocking")

        // or SupervisorJob()
        // it is structured concurrency violation
        val task = launch(start = CoroutineStart.LAZY) { loadDataAsync() }

        task.invokeOnCompletion { e ->
            if (e == null) {
                println("Job completed")
            }
            else {
                println("catch: ${e.message}")
            }
        }

        task.join()

        println("stop runBlocking")
    }

    fun handleCancelException() = runBlocking {
        println("start runBlocking")

        val job = launch {
            try {
                println("try")
                loadDataAsync()
            } catch (e: CancellationException) {
                println("re-throw: ${e.message}")
                throw e
            } catch (e: Throwable) {
                println("catch: ${e.message}")
            } finally {
                withContext(NonCancellable) {
                    println("NonCancellable finally")
                }
            }
        }

        delay(300)
        println("job.cancel()")
        job.cancel()

        println("stop runBlocking")
    }

    fun handleCancelExceptionInWithContext() = runBlocking {
        println("start runBlocking")

        val job = launch {
            try {
                println("try")
                withContext(Dispatchers.IO) {
                    loadDataAsync()
                }
            } catch (e: Throwable) {
                println("catch: ${e.message}")
            } finally {
                println("finally")
            }
        }

        delay(300)
        println("job.cancel()")
        job.cancel()

        println("stop runBlocking")
    }

    // TODO("DON'T DO IT")
//    fun handleAwaitException() = runBlocking {
//        println("start runBlocking")
//        var task: Deferred<String>? = null
//        try {
//            println("try")
//            task = async { loadDataAsync() }
//        } catch (e: Throwable) {
//            println("catch: ${e.message}")
//        } finally {
//            println("finally")
//        }
//
//        try {
//            println("try 2")
//            val result = task?.await()
//            println("Result: $result")
//        } catch (e: Throwable) {
//            println("catch 2: ${e.message}")
//        } finally {
//            println("finally 2")
//        }
//        println("stop runBlocking")
//    }
}
