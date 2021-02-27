import classes_06.Person

fun main() {
    val person1 = Person(187, 85, "Denis")
    val person2 = Person(187, 85, "Denis")
    val person3 = Person(174, 78, "Anton")
    val set = mutableSetOf(person1, person2)
    println(set.size)//домашнее задание пункт №4
    set.add(person3)//домашнее задание пункт №6
    set.forEach {
        println(it)//домашнее задание пункт №6
    }

    person1.buyPet()
    person1.buyPet()
    println("Множество зверей = ${person1.pets}")

    person2.buyPet()
    person2.buyPet()
    person2.buyPet()
    println("Множество зверей = ${person2.pets}")

    person3.buyPet()
    person3.buyPet()
    person3.buyPet()
    person3.buyPet()
    println("Множество зверей = ${person3.pets}")
}