import classes_06.Person

fun main() {
    val person1=Person(187, 85, "Denis")
    val person2=Person(187, 85, "Denis")
    var set = mutableSetOf(person1, person2)
    println(set.size)
}