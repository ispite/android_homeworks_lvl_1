import classes_08.Queue
import classes_08.Result
import kotlin.reflect.full.isSubclassOf

fun main() {
    println(listOfNumbers(listOf(1, 2, 3, 4, 5))) //список целых чисел
    println(listOfNumbers(listOf(1.0, 2.0, 3.0, 4.0, 5.0))) //список вещественных чисел
    println(listOfNumbers(listOf(1.0, 2, 3.0, 4, 5.0))) //список вещественных и целых чисел
    println(listOfNumbers(listOf("1", "2", "3", "4", "5"))) //список строк
    println(listOfNumbers(listOf("1", 2, "3", 4.0, "5"))) //список разных типов

    val queue = Queue<String>()
    queue.enqueue("qwerty")
    queue.enqueue("asdfgh")
    queue.enqueue("zxcvbn")

    println(queue.dequeue())
    println(queue.dequeue())
    println(queue.dequeue())
    println(queue.dequeue())

//    val resultIntString = Result.ResultObject
//    val checkMethod = resultIntString.learningVariance()
//    var resultNumberString = Result.ResultObjectNumberString
//    resultNumberString = checkMethod
//    var resultAnyString = Result.ResultObjectAnyString
//    resultAnyString = checkMethod
//    var resultIntCharSequence = Result.ResultObjectIntCharSequence
//    resultIntCharSequence = checkMethod
//    var resultIntAny = Result.ResultObjectIntAny
//    resultIntAny = checkMethod
}

inline fun <reified T> listOfNumbers(list: List<T>): List<T> {
    return when {
        T::class.isSubclassOf(Number::class) -> list.slice(0..list.lastIndex step 2)
        else -> list
    }
}

fun learningVarianceExternal(): Result<out Int, in String> {
    return Result.ResultObject
}
