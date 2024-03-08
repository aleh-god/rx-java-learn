fun main(args: Array<String>) {
    println("Start main")

    val runner = Runner()

    runner.runCompletableAndCompletable()
//    runner.runCoroutines()

    println("Stop main")
}
