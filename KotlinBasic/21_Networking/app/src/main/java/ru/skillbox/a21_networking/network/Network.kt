package ru.skillbox.a21_networking.network

import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.skillbox.a21_networking.movie_search.API_KEY

object Network {

    val flipperNetworkPlugin = NetworkFlipperPlugin()

    private val client = OkHttpClient.Builder()
        .addNetworkInterceptor(APIKeyAdderInterceptor(API_KEY))
        .build()

    fun getSearchMovieCall(text: String): Call {
        //http://www.omdbapi.com/?apikey=[yourkey]&s=

        val url = HttpUrl.Builder()
            .scheme("http")
            .host("www.omdbapi.com")
            //.addQueryParameter("apikey", API_KEY)
            .addQueryParameter("s", text)
            .build()

        //Log.d("Network getSearch", "Network: $url")

        val request = Request.Builder()
            .get()
            .url(url)
            .build()

        return client.newCall(request)
    }

    fun getSearchWithParametersMovieCall(
        title: String,
        yearOfProduction: String,
        typeOfVideo: String
    ): Call {

        val url = HttpUrl.Builder()
            .scheme("http")
            .host("www.omdbapi.com")
            //.addQueryParameter("apikey", API_KEY)
            .addQueryParameter("s", title)
            .addQueryParameter("y", yearOfProduction)
            .addQueryParameter("type", typeOfVideo)
            .build()

        //Log.d("Network getSearchWithParams", "Network: $url")

        val request = Request.Builder()
            .get()
            .url(url)
            .build()

        return client.newCall(request)
    }
}