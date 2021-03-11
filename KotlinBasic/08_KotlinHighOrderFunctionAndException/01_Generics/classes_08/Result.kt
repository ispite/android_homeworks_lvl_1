package classes_08

sealed class Result<T, R> {
    data class Success<T, R>(val data: T) : Result<T, R>()

    data class Error<T, R>(val exception: R) : Result<T, R>()

    fun learningVariance(): Result<out Int, in String> {
        return ResultObject
    }

    object ResultObject : Result<Int, String>()
    object ResultObjectNumberString : Result<Number, String>()
    object ResultObjectAnyString : Result<Any, String>()
    object ResultObjectIntCharSequence : Result<Int, CharSequence>()
    object ResultObjectIntAny : Result<Int, Any>()

}
