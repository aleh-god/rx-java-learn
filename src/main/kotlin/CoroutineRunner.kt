import coroutines.SuspendCoroutine

class CoroutineRunner {

    fun runCoroutines() {
//        val coroutineException = CoroutineException()
//        coroutineException.handleCancelException()

        val suspendCoroutine = SuspendCoroutine()
        suspendCoroutine.executeDelay()
    }
}