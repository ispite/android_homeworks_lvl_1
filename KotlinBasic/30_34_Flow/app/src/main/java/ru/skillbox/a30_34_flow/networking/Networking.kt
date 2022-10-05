package ru.skillbox.a30_34_flow.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import ru.skillbox.a30_34_flow.data.ApiKey.API_KEY

object Networking {

    private val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(ApiKeyAdderInterceptor(API_KEY))
        .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://www.omdbapi.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .build()

    val omdbApi: OmdbApi
        get() = retrofit.create()
}