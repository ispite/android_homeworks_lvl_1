package ru.skillbox.a31_35_backgroundwork.network

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface Api {

    @GET
    suspend fun getFile(
        @Url url: String
    ): ResponseBody
}