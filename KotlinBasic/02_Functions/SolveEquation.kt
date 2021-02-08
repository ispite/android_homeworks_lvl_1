import kotlin.math.*

fun main() {
    var solutionSum: Double
    solutionSum = solveEquation(a = 3, b = 2, c = 1)
    println("сумма корней $solutionSum")
    solutionSum = solveEquation(a = 5, b = 3, c = 0)
    println("сумма корней $solutionSum")
    solutionSum = solveEquation(a = 2, b = 0, c = 0)
    println("сумма корней $solutionSum")
    solutionSum = solveEquation(a = 0, b = 3, c = 2)
    println("сумма корней $solutionSum")
}

fun solveEquation(a: Int, b: Int, c: Int): Double {
    val x1: Double
    val x2: Double
    val d: Double = b * b - 4.0 * a * c //calculate the discriminant
    return if (d > 0) {
        x1 = (-b + sqrt(d)) / (2 * a)//calculate the roots of the equation if discriminant above 0
        x2 = (-b - sqrt(d)) / (2 * a)//calculate the roots of the equation if discriminant above 0
        println("x1=$x1 x2=$x2")
        x1 + x2
    } else if (d == 0.0) {
        x1 = (-b) / (2.0 * a) //calculate the root of the equation if discriminant is 0
        println("x1=$x1")
        x1
    } else {
        println("рациональных корней нет") //the equation has no real solutions.
        Double.NaN
    }
}