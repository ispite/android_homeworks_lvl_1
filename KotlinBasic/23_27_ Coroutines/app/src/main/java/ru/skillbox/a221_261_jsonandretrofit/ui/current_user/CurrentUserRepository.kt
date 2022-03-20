package ru.skillbox.a221_261_jsonandretrofit.ui.current_user

import android.util.Log
import ru.skillbox.a221_261_jsonandretrofit.data.RemoteUser
import ru.skillbox.a221_261_jsonandretrofit.network.Networking

class CurrentUserRepository {

    suspend fun searchUsers(
        query: String
    ): List<RemoteUser> {
        Log.d("Repository", "searchUsers: $query")
        return Networking.githubApi.searchUsers(query).items
    }

    suspend fun getAuthenticatedUser(
    ): RemoteUser {
        return Networking.githubApi.getAuthenticatedUser()
    }

    suspend fun getUserIsFollowing(): List<RemoteUser> {
        return Networking.githubApi.getUserIsFollowing()
    }
}