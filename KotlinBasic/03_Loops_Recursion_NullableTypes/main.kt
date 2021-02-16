fun main() {
    print("Введите число повторов: ")
    val n = readLine()?.toIntOrNull() ?: return
    val list = loopForNumbersList(n)
    println("Число положительных чисел = ${positiveCount(list)}")
    val sumPoint5 = getSumOfNumbers(list)
    println("Сумма положительных чисел = $sumPoint5")
    print("Введите число для расчета наибольшего общего делителя: ")
    val a = readLine()?.toIntOrNull() ?: return
    println("Наибольший общий делитель $sumPoint5 и $a = ${largestCommonDivisor(a, sumPoint5)}")

/* то же самое только первая функция возращает массив */
    val arrList = loopForNumbers(n).toList()
    println("Число положительных чисел = ${positiveCount(arrList)}")
    val sumPoint5arr = getSumOfNumbers(arrList)
    println("Сумма положительных чисел = $sumPoint5arr")
    print("Введите число для расчета наибольшего общего делителя: ")
    val aArr = readLine()?.toIntOrNull() ?: return
    println("Наибольший общий делитель $sumPoint5arr и $aArr = " +
            "${largestCommonDivisor(aArr, sumPoint5arr)}")
}

fun loopForNumbers(n: Int): IntArray {
    val array = IntArray(n)
    var i = 0
    while (i < n) {
        println("Введите число № ${i + 1}")
        array[i] = readLine()?.toIntOrNull() ?: continue
        i++
    }
    return array
}

fun loopForNumbersList(n: Int): List<Int> {
    val array: MutableList<Int> = mutableListOf()
    var i = 0
    while (i < n) {
        println("Введите число № ${i + 1}")
        array.add(readLine()?.toIntOrNull() ?: continue)
        i++
    }
    return array
}

fun positiveCount(list: List<Int>): Int {
    var count = 0
    var i = 0
    while (i in list.indices) {
        if (list[i] > 0) count++
        i++
    }
    return count
}

fun getSumOfNumbers(list: List<Int>): Int {
    var sum = 0
    var i = 0
    while (i in list.indices) {
        if (list[i] > 0) sum += list[i]
        i++
    }
    return sum
}

tailrec fun largestCommonDivisor(a: Int, b: Int): Int {
    if (b == 0)
        return kotlin.math.abs(a)
    return largestCommonDivisor(b, a % b)
}

