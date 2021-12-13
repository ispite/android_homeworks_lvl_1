package ru.skillbox.a22_26_jsonandretrofit.network

import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request


object Network {

    private val client = OkHttpClient.Builder()
        .build()

    fun getSearchMovieCall(text: String): Call {
        //http://www.omdbapi.com/?apikey=[yourkey]&s=

        val url = HttpUrl.Builder()
            .scheme("http")
            .host("www.omdbapi.com")
            .addQueryParameter("apikey", API_KEY)
            .addQueryParameter("s", text)
            .build()

        //Log.d("Network getSearch", "Network: $url")

        val request = Request.Builder()
            .get()
            .url(url)
            .build()

        return client.newCall(request)
    }

/*    fun getSearchWithParametersMovieCall(
        title: String,
        yearOfProduction: String,
        typeOfVideo: String
    ): Call {

        val url = HttpUrl.Builder()
            .scheme("http")
            .host("www.omdbapi.com")
            .addQueryParameter("apikey", API_KEY)
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
    }*/
}