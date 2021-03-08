fun main() {
    println(listOfNumbers(listOf(1, 2, 3, 4, 5)))
    println(listOfNumbers(listOf("1", "2", "3", "4", "5")))
    println(listOfNumbers(listOf("1", 2, "3", 4.0, "5")))
}

fun <T> listOfNumbers(list:List<T>):List<T>{
    return if (list is List<Number>) {
        list.slice(0..list.lastIndex step 2)
    } else list
}

/*
inline fun <reified T> listOfNumbers(list:List<T>):List<T>{
    return if (list is List<Number>) {
        list.slice(0..list.lastIndex step 2)
    } else list
}*/
