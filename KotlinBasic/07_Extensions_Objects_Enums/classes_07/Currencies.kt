package classes_07

enum class Currencies(val exchangeRate: Double) {
    RUS_ROUBLES(73.98),
    US_DOLLARS(1.00),
    EUROS(0.84);

    companion object {
        var nationalCurrency = RUS_ROUBLES
    }
}