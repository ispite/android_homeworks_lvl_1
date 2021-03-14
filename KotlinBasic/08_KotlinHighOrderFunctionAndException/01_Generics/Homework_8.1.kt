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
    object1.c1 = object1.returnedRandomResult()
    object1.c2 = object1.returnedRandomResult()
    object1.d1 = object1.returnedRandomResult()
    object1.d2 = object1.returnedRandomResult()
//    object1.e1 = object1.returnedRandomResult()
//    object1.e2 = object1.returnedRandomResult()
//    object1.f1 = object1.returnedRandomResult()
//    object1.f2 = object1.returnedRandomResult()
}

inline fun <reified T> listOfNumbers(list: List<T>): List<T> {
    return when {
        T::class.isSubclassOf(Number::class) -> list.slice(0..list.lastIndex step 2)
        else -> list
    }
}