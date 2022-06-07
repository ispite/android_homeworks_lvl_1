package ru.skillbox.a25_29_contentprovider.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create

object Networking {

    private val okHttpClient = OkHttpClient.Builder()
        .followRedirects(true)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://google.com")
        .client(okHttpClient)
        .build()

    val api: Api
        get() = retrofit.create()
}