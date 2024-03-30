import coroutines.AwaitSharing

class CoroutineRunner {

    fun runCoroutines() {
//        val coroutineException = CoroutineException()
//        coroutineException.handleCancelException()

//        val suspendCoroutine = SuspendCoroutine()
//        suspendCoroutine.executeDelay()

        val awaitSharing = AwaitSharing()
        awaitSharing.executeWorks()
    }
}