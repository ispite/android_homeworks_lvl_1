package ru.skillbox.a221_261_jsonandretrofit.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import ru.skillbox.a221_261_jsonandretrofit.data.AuthConfig

object Networking {
    private val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(AccessTokenInterceptor(AuthConfig.TOKEN))
        .addNetworkInterceptor(HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .build()


    val githubApi: GithubApi
        get() = retrofit.create()
}