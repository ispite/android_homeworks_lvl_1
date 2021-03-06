import classes_07.Currencies
import classes_07.Wallets

fun main() {
    val wallet1 = Wallets.VirtualWallet
    val wallet2 = Wallets.RealWallet
    wallet1.addMoney(Currencies.RUS_ROUBLES, 123.20)
    wallet2.addMoney(Currencies.RUS_ROUBLES, 100, 23)
    println("Деньги в долларах в кошельке №1: " +
            "${wallet1.moneyInUSD2(CurrencyConverter.rusRoubleExchange, CurrencyConverter.euroExchange)}")
    println("Деньги в долларах в кошельке №2: " +
            "${wallet2.moneyInUSD2(CurrencyConverter.rusRoubleExchange, CurrencyConverter.euroExchange)}")

    println("Валюта конвертированная в доллары: ${Currencies.RUS_ROUBLES.convertToUsd(15)}")
    println("Валюта конвертированная в доллары: ${Currencies.US_DOLLARS.convertToUsd(15)}")
    println("Валюта конвертированная в доллары: ${Currencies.EUROS.convertToUsd(15)}")
}

val Currencies.expandFunctionality: Boolean
    get() {
        return this == Currencies.nationalCurrency
    }

object CurrencyConverter {
    const val rusRoubleExchange = 73.98
    const val euroExchange = 0.84
}

fun Currencies.convertToUsd(amount: Int): Double {
    return amount / this.exchangeRate
}