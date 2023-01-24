package ru.skillbox.a30_34_flow.networking

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyAdderInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val modifiedUrl = originalRequest.url.newBuilder()
            .addQueryParameter("apikey", apiKey)
            .build()
        val modifiedRequest = originalRequest.newBuilder().url(modifiedUrl).build()
        return chain.proceed(modifiedRequest)
    }
}