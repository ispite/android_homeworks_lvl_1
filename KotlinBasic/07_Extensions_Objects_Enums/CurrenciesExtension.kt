import classes_07.Currencies

val Currencies.expandFunctionality: Boolean
    get() {
        return this == Currencies.nationalCurrency
    }

fun Currencies.convertToUsd(amount: Int): Double {
    return amount / this.exchangeRate
}