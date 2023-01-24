package ru.skillbox.a34_38_Release_Flow.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import ru.skillbox.a34_38_Release_Flow.data.ApiKey.API_KEY
import ru.skillbox.a34_38_Release_Flow.utils.haveP

object Networking {

    private val baseUrlProtocol: String = if (haveP()) "https" else "http"

    private val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(ApiKeyAdderInterceptor(API_KEY))
        .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("$baseUrlProtocol://www.omdbapi.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .build()

    val omdbApi: OmdbApi
        get() = retrofit.create()
}