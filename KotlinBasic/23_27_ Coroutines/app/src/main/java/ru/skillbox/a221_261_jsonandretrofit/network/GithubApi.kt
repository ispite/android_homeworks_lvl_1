package ru.skillbox.a221_261_jsonandretrofit.network

import retrofit2.Call
import retrofit2.http.*
import ru.skillbox.a221_261_jsonandretrofit.data.RemoteBio
import ru.skillbox.a221_261_jsonandretrofit.data.RemoteRepository
import ru.skillbox.a221_261_jsonandretrofit.data.RemoteUser

interface GithubApi {

    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String
    ): ServerItemsWrapper<RemoteUser>

    @GET("/user")
    suspend fun getAuthenticatedUser(): RemoteUser

    @GET("/user/following")
    suspend fun getUserIsFollowing(): List<RemoteUser>

    @GET("/user/repos")
    fun getAuthenticatedRepos(): Call<List<RemoteRepository>>

    @GET("/user/starred/{owner}/{repo}")
    fun getRepoDetail(
        @Path("owner") ownerName: String,
        @Path("repo") repositoryName: String
    ): Call<String>

    @PUT("/user/starred/{owner}/{repo}")
    suspend fun starRepo(
        @Path("owner") ownerName: String,
        @Path("repo") repositoryName: String
    ): Call<String>

    @DELETE("/user/starred/{owner}/{repo}")
    suspend fun unstarRepo(
        @Path("owner") ownerName: String,
        @Path("repo") repositoryName: String
    ): Call<String>

    @GET("/users/{username}/starred")
    fun getStarredRepos(
        @Path("username") ownerName: String,
    ): Call<List<RemoteRepository>>

    @PATCH("/user")
    fun changeBio(
        @Body bio: RemoteBio
    ): Call<String>
}