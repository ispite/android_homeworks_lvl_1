import classes_08_3.Wheel

fun main() {
    val wheel = Wheel()
    wheel.check()
    wheel.catchPressureException(-2.0)
    wheel.catchPressureException(2.0)
    wheel.catchPressureException(20.0)
    wheel.check()
}