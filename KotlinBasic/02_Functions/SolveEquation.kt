import kotlin.math.*

fun main() {
    var solutionSum:Double
    solutionSum = solveEquation(a=3, b=2, c=1)
    println("сумма корней $solutionSum")
    solutionSum = solveEquation(a=5, b=3, c=0)
    println("сумма корней $solutionSum")
    solutionSum = solveEquation(a=2, b=0, c=0)
    println("сумма корней $solutionSum")
    solutionSum = solveEquation(a=0, b=3, c=2)
    println("сумма корней $solutionSum")
}

fun solveEquation(a:Int,b:Int,c:Int):Double {
    val x1 : Double
    val x2 : Double
    val D : Double
    D = ((b*b) - (4 * a * c)).toDouble() //calculate the discriminant
    if (D > 0) {
        x1 = (-b+sqrt(D))/(2*a)//calculate the roots of the equation if discriminant above 0
        x2 = (-b-sqrt(D))/(2*a)//calculate the roots of the equation if discriminant above 0
        println("x1=$x1 x2=$x2")
        return x1 + x2
    } else if (D == 0.0) {
        x1 = ((-b) / (2 * a)).toDouble() //calculate the root of the equation if discriminant is 0
        println("x1=$x1")
        return x1
    } else {
        println("рациональных корней нет") //the equation has no real solutions.
        return Double.NaN
    }
}