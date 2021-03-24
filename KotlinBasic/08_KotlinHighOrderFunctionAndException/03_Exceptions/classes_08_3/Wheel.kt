package classes_08_3

class Wheel {
    var pressure = 0.0
        private set

    private var oldPressure = 0.0

    class TooHighPressure(message: String) : Exception(message)

    class TooLowPressure(message: String) : Exception(message)

    class IncorrectPressure(message: String) : Exception(message)

    private fun setPressure(value: Double) {
        oldPressure = pressure
        pressure = value
        if (pressure > 10) {
            print("BOOM! ")
        }
        check()
        println("Success pressure set $pressure")
    }

    fun catchPressureException(value: Double): Boolean {
        try {
            setPressure(value)
            return true
        } catch (t: Throwable) {
            pressure = oldPressure
            println("Set pressure message ${t.message}")
        }
        return false
    }

    private fun check() {
        when (pressure) {
            in -1 * Double.MAX_VALUE..-1 * Double.MIN_VALUE -> throw IncorrectPressure("Incorrect Pressure")
            in 0.0..1.6 -> throw TooLowPressure("Too Low Pressure")
            in 2.5..Double.MAX_VALUE -> throw TooHighPressure("Too High Pressure")
        }
    }

    fun catchCheck() {
        try {
            check()
        } catch (t: Throwable) {
            println("Check pressure message ${t.message}")
        }
    }


}