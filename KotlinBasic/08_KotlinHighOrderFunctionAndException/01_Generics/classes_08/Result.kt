package classes_08

import kotlin.random.Random

sealed class Result<T, R> {
    data class Success<T, R>(val data: T) : Result<T, R>()

    data class Error<T, R>(val exception: R) : Result<T, R>()

    fun returnedRandomResult(): Result<Int, String> {
        val success: Result<Int,String> = Success(10)
        val error: Result<Int,String> = Error("error")
        return if (Random.nextBoolean()) success else error
    }

    var c1 : Result<out Number, in String> = Success(0)
    var c2 : Result<out Number, in String> = Error("0")
    var d1 : Result<out Any, in String> = Success(0)
    var d2 : Result<out Any, in String> = Error("0")

    var e1 : Result<Int, CharSequence> = Success(0)
    var e2 : Result<Int, CharSequence> = Error("0")
    var f1 : Result<Int, Any> = Success(0)
    var f2 : Result<Int, Any> = Error("0")
}
