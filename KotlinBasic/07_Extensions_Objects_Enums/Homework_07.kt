import classes_07.Currencies

fun main() {
    val currencyConverter = Currencies.RUS_ROUBLES

}

val Currencies.expandFunctionality : Boolean
    get() {
        this.expandFunctionality == Currencies.nationalCurrency
    }

object CurrencyConverter {
    val rusRoubleExchange = 73.98
    val euroExchange = 1.21;
}

fun Currencies.convertToUsd(amount: Int):Double {
    return amount/73.98
}

