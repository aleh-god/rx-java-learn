fun main(args: Array<String>) {
    println("Start main")

//    val rxRunner = RxRunner()
//    rxRunner.runCompletableAndCompletable()

    val coroutineRunner = CoroutineRunner()
    coroutineRunner.runCoroutines()

    println("Stop main")
}
