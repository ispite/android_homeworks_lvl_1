fun main() {
    print("Введите число повторов: ")
    val n = readLine()?.toIntOrNull() ?: return
//    println(loopForPhoneList(n))
    val phoneList = loopForPhoneListString(n)
    println("Номера начинающиеся на +7 ${listSpecified(phoneList)}")
    println("Количество уникальных введённых номеров = ${sizeOfSet(phoneList)}")
    println("Длина всех введённых номеров = ${sumLengthPhones(phoneList)}")
    val testMap = createPhoneMap(phoneList)
    printResult(testMap)
//    printResultPair(testMap)
}

fun loopForPhoneListString(n: Int): List<String> {
    val phoneList: MutableList<String> = mutableListOf()
    var i = 0
    var temp: String?
    while (i < n) {
        println("Введите номер телефона № ${i + 1}")
        temp = readLine()
        if (temp != null && temp.removePrefix("+").all() { it.isDigit() }) {
            phoneList.add(temp)
        } else continue
        i++
    }
    return phoneList
}

//Другой способ собрать список телефонов пользователей
fun loopForPhoneList(n: Int): List<String> {
    val list: MutableList<Long> = mutableListOf()
    val listString: MutableList<String> = mutableListOf()
    var i = 0
    while (i < n) {
        println("Введите номер телефона № ${i + 1}")
        list.add(readLine()?.toLongOrNull() ?: continue)
        listString.add(if (list[i] > 0) {
            '+' + list[i].toString()
        } else list[i].toString())
        i++
    }
    return listString
}

fun listSpecified(list: List<String>, key: String = "+7"): List<String> {
    return list.filter { it.startsWith(key) }
}

fun sizeOfSet(list: List<String>): Int {
    return list.toSet().size
}

fun sumLengthPhones(list: List<String>): Int {
    return list.sumBy { it.length }
}

fun createPhoneMap(list: List<String>): Map<String, String> {
    val map = mutableMapOf<String, String>()
    for (i in list) {
        print("Введите имя человека с номером телефона $i: ")
        readLine()?.let { map.put(i, it) }
    }
    return map
}

//Печать результата с помощью entry
fun printResult(map: Map<String, String>) {
    for (i in map) {
        println("Человек: ${map[i.key]}. Номер телефона: ${i.key}")
    }
}

//Печать результата с помощью пары ключ-значение
fun printResultPair(map: Map<String, String>) {
    for ((k, v) in map) {
        println("Человек: $v. Номер телефона: $k")
    }
}