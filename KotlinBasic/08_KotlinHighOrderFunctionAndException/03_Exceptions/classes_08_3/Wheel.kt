package classes_08_3

class Wheel {
    var pressure: Double = 0.0
        private set

    class TooHighPressure {
        init {
            throw Exception("Too High Pressure")
        }
    }

    class TooLowPressure {
        init {
            throw Exception("Too Low Pressure")
        }
    }

    class IncorrectPressure {
        init {
            throw Exception("Incorrect Pressure")
        }
    }

    fun setPressure(value: Double): Any {
        return try {
            return when {
                value < 0.0 -> IncorrectPressure()
                value > 10.0 -> { print("BOOM! ")
                    TooHighPressure()}
                else -> pressure = value
            }
        } catch (t: Throwable) {
            println("Set pressure message ${t.message}")
        }
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