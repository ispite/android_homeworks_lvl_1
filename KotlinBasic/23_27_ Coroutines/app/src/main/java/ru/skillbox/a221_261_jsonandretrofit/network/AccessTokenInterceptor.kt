package ru.skillbox.a221_261_jsonandretrofit.network

import okhttp3.Interceptor
import okhttp3.Response

class AccessTokenInterceptor(private val accessToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val modifiedRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "token $accessToken")
            .build()

        return chain.proceed(modifiedRequest)
    }
}