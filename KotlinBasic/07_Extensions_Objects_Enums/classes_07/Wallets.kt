package classes_07

sealed class Wallets {
    object VirtualWallet : Wallets() {
        private var currencyRoubles: Double = 0.0
        private var currencyDollars: Double = 0.0
        private var currencyEuros: Double = 0.0

        fun addMoney(typeCurrency: Currencies, quantityMoney: Double) {
            when (typeCurrency) {
                Currencies.RUS_ROUBLES -> currencyRoubles = quantityMoney
                Currencies.US_DOLLARS -> currencyDollars = quantityMoney
                Currencies.EUROS -> currencyEuros = quantityMoney
            }
        }

        //      конвертация валюты с помощью enum класса
        override fun moneyInUSD(): Double = currencyRoubles / Currencies.RUS_ROUBLES.exchangeRate +
                currencyEuros / Currencies.EUROS.exchangeRate

        //      конвертация валюты с помощью передаваемых значений
        override fun moneyInUSD(rusRoubleExchange: Double, euroExchange: Double): Double =
                currencyRoubles / rusRoubleExchange + currencyEuros / euroExchange
    }

    object RealWallet : Wallets() {
        private val currencyRoubles: MutableMap<Int, Int> = mutableMapOf(0 to 0)
        private val currencyDollars: MutableMap<Int, Int> = mutableMapOf(0 to 0)
        private val currencyEuros: MutableMap<Int, Int> = mutableMapOf(0 to 0)

        fun addMoney(typeCurrency: Currencies, faceValue: Int, quantityBanknotes: Int) {
            when (typeCurrency) {
                Currencies.RUS_ROUBLES -> currencyRoubles[faceValue] = quantityBanknotes
                Currencies.US_DOLLARS -> currencyDollars[faceValue] = quantityBanknotes
                Currencies.EUROS -> currencyEuros[faceValue] = quantityBanknotes
            }
        }

        //      конвертация валюты с помощью enum класса
        override fun moneyInUSD() = run {
            var sum = 0.0
            currencyRoubles.forEach { (k, v) ->
                sum += k * v / Currencies.RUS_ROUBLES.exchangeRate
            }
            currencyEuros.forEach { (k, v) ->
                sum += k * v / Currencies.EUROS.exchangeRate
            }
            sum
        }

        //      конвертация валюты с помощью передаваемых значений
        override fun moneyInUSD(rusRoubleExchange: Double, euroExchange: Double) = run {
            var sum = 0.0
            currencyRoubles.forEach { (k, v) ->
                sum += k * v / rusRoubleExchange
            }
            currencyEuros.forEach { (k, v) ->
                sum += k * v / euroExchange
            }
            sum
        }
    }

    abstract fun moneyInUSD(): Double
    abstract fun moneyInUSD(rusRoubleExchange: Double, euroExchange: Double): Double
}
