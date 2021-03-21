import classes_08_3.Wheel

fun main() {
    val wheel = Wheel()
    wheel.check()
    wheel.setPressure(-2.0)
    wheel.setPressure(2.0)
    wheel.setPressure(20.0)
    wheel.check()
}