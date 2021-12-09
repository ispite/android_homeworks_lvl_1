package ru.skillbox.a21_networking.network

import okhttp3.Interceptor
import okhttp3.Response

class APIKeyAdderInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val modifiedURL = originalRequest.url.newBuilder()
            .addQueryParameter("apikey", apiKey)
            .build()
        val modifiedRequest = originalRequest.newBuilder().url(modifiedURL).build()
        //chain.proceed(modifiedRequest)
        return chain.proceed(modifiedRequest)
    }
}