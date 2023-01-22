package ru.skillbox.dependency_injection.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import ru.skillbox.dependency_injection.data.Api
import timber.log.Timber
import javax.inject.Singleton


@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        Timber.tag("Logging").d("providesOkHttpClient")
        return OkHttpClient.Builder().followRedirects(true).build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        Timber.tag("Logging").d("providesRetrofit")
        return Retrofit.Builder().baseUrl("https://google.com").client(okHttpClient).build()
    }

    @Singleton
    @Provides
    fun providesApi(retrofit: Retrofit): Api {
        Timber.tag("Logging").d("providesApi")
        return retrofit.create(Api::class.java)
    }
}