package classes_08

sealed class Result<T, R> {
    data class Success<T>(val data:T): Result(){
    }

    data class Error<R>(val exception:R): Result(){
    }

    fun qwe():Result<Int, String>{
        var qwe = result
        return
    }

    object result : Result<Int, String>()

    //https://medium.com/androiddevelopers/sealed-with-a-class-a906f28ab7b5
    //https://medium.com/android-dev-hacks/explore-kotlin-sealed-classes-7f54ddb74f28
    //https://proandroiddev.com/understanding-generics-and-variance-in-kotlin-714c14564c47
}
