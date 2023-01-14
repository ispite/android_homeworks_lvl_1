package ru.skillbox.dependency_injection.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.skillbox.dependency_injection.data.Api
import ru.skillbox.dependency_injection.data.AppVersionInterceptor
import ru.skillbox.dependency_injection.data.BindAppVersionInterceptor
import timber.log.Timber
import javax.inject.Qualifier
import javax.inject.Singleton


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BindHttpLoggingInterceptor

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    @BindAppVersionInterceptor
    fun bindAppVersionInterceptor(): Interceptor {
        return AppVersionInterceptor()
    }

    @Provides
    @Singleton
    @BindHttpLoggingInterceptor
    fun bindHttpLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        @BindHttpLoggingInterceptor httpLoggingInterceptor: Interceptor,
        @BindAppVersionInterceptor appVersionInterceptor: Interceptor
    ): OkHttpClient {
        Timber.tag("Logging").d("providesOkHttpClient")
        return OkHttpClient.Builder()
            .addNetworkInterceptor(httpLoggingInterceptor)
            .followRedirects(true).build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        Timber.tag("Logging").d("providesRetrofit")
        return Retrofit.Builder().baseUrl("https://google.com").client(okHttpClient).build()
    }

    @Provides
    @Singleton
    fun providesApi(retrofit: Retrofit): Api {
        Timber.tag("Logging").d("providesApi")
        return retrofit.create(Api::class.java)
    }
}