package ru.skillbox.a221_261_jsonandretrofit.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.skillbox.a221_261_jsonandretrofit.data.RemoteUser

interface GithubApi {

    @GET("search/users")
    fun searchUsers(
        @Query("q") query : String
    ):Call<SeverItemsWrapper<RemoteUser>>

    @GET("/user")
    fun getAuthenticatedUser():Call<RemoteUser>
}