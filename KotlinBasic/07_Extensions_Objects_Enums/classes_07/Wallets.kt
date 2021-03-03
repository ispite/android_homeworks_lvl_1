package classes_07

import sun.security.ec.point.ProjectivePoint

sealed class Wallets{
    object virtualWallet: Wallets(){
        private val currencyRoubles : Double = 1.0
        private val currencyDollars : Double = 2.0
        private val currencyEuros : Double = 3.0

        fun addMoney(typeCurrency:Currencies, quantity: Int){

     }
    }
    object realWallet: Wallets(){
        private val currencyRoubles: MutableMap<Int, Int> = TODO()
        private val currencyDollars: MutableMap<Int, Int> = TODO()
        private val currencyEuros: MutableMap<Int, Int> = TODO()

        fun addMoney(typeCurrency:Currencies, faceValue:Int, quantity:Int){

        }
    }
}
