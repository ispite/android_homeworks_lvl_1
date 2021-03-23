package classes_08_3

class Wheel {
    var pressure = 0.0
        private set

    class TooHighPressure : Exception() {
        init {
            throw Exception("Too High Pressure")
        }
    }

    class TooLowPressure : Exception() {
        init {
            throw Exception("Too Low Pressure")
        }
    }

    class IncorrectPressure : Exception() {
        init {
            throw Exception("Incorrect Pressure")
        }
    }

    private fun setPressure(value: Double): Any {
        return when {
            value < 0.0 -> IncorrectPressure()
            value > 10.0 -> {
                print("BOOM! ")
                TooHighPressure()
            }
            else -> pressure = value
        }
    }

    fun catchPressureException(value: Double): Boolean {
        try {
            setPressure(value)
            return true
        } catch (t: Throwable) {
            println("Set pressure message ${t.message}")
        }
        return false
    }

    fun check() {
        try {
            when (pressure) {
                in Double.MIN_VALUE..0.0 -> IncorrectPressure()
                in 0.0..1.6 -> TooLowPressure()
                in 2.5..Double.MAX_VALUE -> TooHighPressure()
            }
        } catch (t: Throwable) {
            println("Check pressure message ${t.message}")
        }
    }


}