fun main() {
    print("Введите число повторов: ")
    val n = readLine()?.toIntOrNull() ?: return
    val phoneList = loopForPhoneListString(n)
    println("Номера начинающиеся на +7 ${listSpecified(phoneList)}")
    println("Количество уникальных введённых номеров = ${sizeOfSet(phoneList)}")
    println("Длина всех введённых номеров = ${sumLengthPhones(phoneList)}")
    val testMap = createPhoneMap(phoneList)
    printResult(testMap)/*Печать результата с помощью entry*/
    printResultPair(testMap)/*Печать результата с помощью пары ключ-значение*/
    loopForPhoneList(n)/*Другой способ собрать список телефонов пользователей*/
}

fun loopForPhoneListString(n: Int) = mutableListOf<String>().apply {
    var i = 0
    while (i < n) {
        print("Введите номер телефона № ${i + 1} ")
        val temp = readLine()
        temp?.let {
            if (temp.removePrefix("+").all() { it.isDigit() }) {
                add(temp)
                i++
            }
        }
    }
}

//Другой способ собрать список телефонов пользователей
fun loopForPhoneList(n: Int) = mutableListOf<Long>().apply {
    val listString: MutableList<String> = mutableListOf()
    var i = 0
    while (i < n) {
        print("Введите номер телефона № ${i + 1} ")
        this.add(readLine()?.toLongOrNull() ?: continue)
        listString.add(if (this[i] > 0) {
            '+' + this[i].toString()
        } else this[i].toString())
        i++
    }
}

fun listSpecified(list: List<String>, key: String = "+7") = list.filter { it.startsWith(key) }

fun sizeOfSet(list: List<String>) = list.toSet().size

fun sumLengthPhones(list: List<String>) = list.sumBy { it.length }

fun createPhoneMap(list: List<String>) = mutableMapOf<String, String>().apply {
    list.forEach { i ->
        print("Введите имя человека с номером телефона $i: ")
        readLine()?.let { put(i, it) }
    }
}

//Печать результата с помощью entry
fun printResult(map: Map<String, String>) = map.forEach { i -> println("Человек: ${map[i.key]}. Номер телефона: ${i.key}") }

//Печать результата с помощью пары ключ-значение
fun printResultPair(map: Map<String, String>) = map.forEach { (k, v) -> println("Человек: $v. Номер телефона: $k") }