package ru.skillbox.a21_networking.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

lateinit var LastUnsuccessfulRequest : Request

class StoreLastUnsuccessfulRequest():Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val response = chain.proceed(originalRequest)
        if (response.code !in 200..299) {
            Log.d("Interceptor", "intercept: НЕудачный запрос перехвачен")
            LastUnsuccessfulRequest = originalRequest
        }
        return response
    }

}