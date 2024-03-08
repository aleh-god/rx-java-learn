import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import rxjava.*

class Runner {

    fun runCompletableAndThenSingle() {
        val completableAndThenSingle = CompletableAndThenSingle()
        completableAndThenSingle.reloadData("test").subscribe({
            println("completableAndThenSingle.reloadData: $it")
        }) {
            println("error: ${it.message}")
        }
    }

    fun runMaybeToSingle() {
        val maybeToSingle = MaybeToSingle()
        maybeToSingle.getDataBy(1).subscribe({
            println("maybeToSingle: $it")
        }) {
            println("error: ${it.message}")
        }

        maybeToSingle.getDataBy(5).subscribe({
            println("maybeToSingle: $it")
        }) {
            println("error: ${it.message}")
        }
    }

    fun runTwoSinglesZipper() {
        val twoSinglesZipper = TwoSinglesZipper.BaseImpl()
        twoSinglesZipper.zipSources().subscribe({
            println("twoSinglesZipper: $it")
        }) {
            println("error: ${it.message}")
        }
    }

    fun runSingleAndNull() {
        val singleAndNull = SingleAndNull.BaseImpl()
        singleAndNull.getNullableData(1).subscribe({
            println("singleAndNull: $it")
        }) {
            println("error: ${it.message}")
        }

        singleAndNull.getNullableData(5).subscribe({
            println("singleAndNull: $it")
        }) {
            println("error: ${it.message}")
        }
    }

    fun runFlowableToSingle() {
        try {
            val flowableToSingle = FlowableToSingle.BaseImpl()
            val result = flowableToSingle.getResult()
                .blockingGet()
            println("flowableToSingle: $result")
        } catch (e: Exception) {
            println("main error: ${e.message}")
        }
    }

    fun runSingleAndSingleAgain() {
        try {
            val singleAndSingleAgain = SingleAndSingleAgain()
            val result = singleAndSingleAgain.getResult()
                .blockingGet()
            println("SingleAndSingleAgain: $result")
        } catch (e: Exception) {
            println("main error: ${e.message}")
        }
    }

    fun runSingleDoOnSuccessSingle() {
        try {
            val singleDoOnSuccessSingle = SingleDoOnSuccessSingle()
            val result = singleDoOnSuccessSingle.doWork()
                .blockingGet()
            println("Result: $result")
        } catch (e: Exception) {
            println("main error: ${e.message}")
        }
    }

    fun runOnErrorNextAndHandling() {
        val onErrorNextAndHandling = OnErrorNextAndHandling()
        onErrorNextAndHandling
            .doWork()
            .subscribe(
                { println("Result: $it") },
                { println("main error: ${it.message}") }
            )
    }

    fun runCompletableAndCompletable() {
        val completableAndCompletable = CompletableAndCompletable()
        completableAndCompletable
            .operateData("data")
            .subscribe(
                { println("complete operating") },
                { println("main error: ${it.message}") }
            )
    }

    fun runCoroutines() = runBlocking<Unit> {
        val a = async {
            println("I'm computing part of the answer")
            6
        }
        val b = async {
            println("I'm computing another part of the answer")
            7
        }
        println("The answer is ${a.await() * b.await()}")
    }
}