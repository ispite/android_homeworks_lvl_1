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

        override fun moneyInUSD(): Double = currencyRoubles / 73.98 + currencyEuros / 0.84

        override fun moneyInUSD2(rusRoubleExchange: Double, euroExchange: Double): Double =
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

        override fun moneyInUSD(): Double {
            var sum = 0.0
            currencyRoubles.forEach { (k, v) ->
                sum += k * v / 73.98
            }
            currencyEuros.forEach { (k, v) ->
                sum += k * v / 0.84
            }
            return sum
        }

/*        override fun moneyInUSD() = Double.apply{
            currencyRoubles.forEach{ (k, v) ->
                this += k*v/73.98
            }
            currencyEuros.forEach{ (k, v) ->
                this += k*v/0.84
            }
        }*/

        override fun moneyInUSD2(rusRoubleExchange: Double, euroExchange: Double): Double {
            var sum = 0.0
            currencyRoubles.forEach { (k, v) ->
                sum += k * v / rusRoubleExchange
            }
            currencyEuros.forEach { (k, v) ->
                sum += k * v / euroExchange
            }
            return sum
        }
    }

    abstract fun moneyInUSD(): Double
    abstract fun moneyInUSD2(rusRoubleExchange: Double, euroExchange: Double): Double
}
