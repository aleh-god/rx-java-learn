import coroutines.CoroutineException

class CoroutineRunner {

    fun runCoroutines() {
        val coroutineException = CoroutineException()
//        coroutineException.handleCancelException()
        coroutineException.handleCancelExceptionInWithContext()

//        val suspendCoroutine = SuspendCoroutine()
//        suspendCoroutine.executeDelay()

//        val awaitSharing = AwaitSharing()
//        awaitSharing.executeWorks()
    }
}