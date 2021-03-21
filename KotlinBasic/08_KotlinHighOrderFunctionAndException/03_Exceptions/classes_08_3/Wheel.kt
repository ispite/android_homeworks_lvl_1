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
        return when {
            value < 0.0 -> try {
                IncorrectPressure()
            } catch (t: Throwable) {
                println("Exception message ${t.message}")
            }
            value < 1.6 -> try {
                TooLowPressure()
            } catch (t: Throwable) {
                println("Exception message ${t.message}")
            }
            value > 2.5 -> try {
                TooHighPressure()
            } catch (t: Throwable) {
                println("Exception message ${t.message}")
            }
            else -> pressure = value
        }
    }

    fun check() {
        when (pressure) {
            in Double.MIN_VALUE..0.0 -> try {
                IncorrectPressure()
            } catch (t: Throwable) {
                println("Exception message ${t.message}")
            }
            in 0.0..1.6 -> try {
                TooLowPressure()
            } catch (t: Throwable) {
                println("Exception message ${t.message}")
            }
            in 2.5..Double.MAX_VALUE -> try {
                TooHighPressure()
            } catch (t: Throwable) {
                println("Exception message ${t.message}")
            }

        }
    }
}