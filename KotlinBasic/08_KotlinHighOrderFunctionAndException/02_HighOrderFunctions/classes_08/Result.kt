package classes_08

import kotlin.random.Random

sealed class Result<T, R> {
    data class Success<T, R>(val data: T) : Result<T, R>()

    data class Error<T, R>(val exception: R) : Result<T, R>()

    fun returnedRandomResult(): Result<Int, String> {
        val success: Result<Int, String> = Success(10)
        val error: Result<Int, String> = Error("error")
        return if (Random.nextBoolean()) success else error
    }
}
