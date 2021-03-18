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

    val object1 = Result.Success<Int, String>(12)

    var c1: Result<out Number, in String> = Result.Success(0)
    var c2: Result<out Number, in String> = Result.Error("0")
    var d1: Result<out Any, in String> = Result.Success(0)
    var d2: Result<out Any, in String> = Result.Error("0")

    var e1: Result<Int, CharSequence> = Result.Success(0)
    var e2: Result<Int, CharSequence> = Result.Error("0")
    var f1: Result<Int, Any> = Result.Success(0)
    var f2: Result<Int, Any> = Result.Error("0")

    c1 = object1.returnedRandomResult()
    println(c1)
    c2 = object1.returnedRandomResult()
    println(c2)
    d1 = object1.returnedRandomResult()
    println(d1)
    d2 = object1.returnedRandomResult()
    println(d2)
//    e1 = object1.returnedRandomResult()
//    e2 = object1.returnedRandomResult()
//    f1 = object1.returnedRandomResult()
//    f2 = object1.returnedRandomResult()

    println(queue.filter {  })

}

inline fun <reified T> listOfNumbers(list: List<T>): List<T> {
    return when {
        T::class.isSubclassOf(Number::class) -> list.slice(0..list.lastIndex step 2)
        else -> list
    }
}