package ru.skillbox.a221_261_jsonandretrofit.ui.current_user

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.skillbox.a221_261_jsonandretrofit.data.RemoteUser
import ru.skillbox.a221_261_jsonandretrofit.network.Networking
import ru.skillbox.a221_261_jsonandretrofit.network.SeverItemsWrapper

class CurrentUserRepository {

    fun searchUsers(
        query: String,
        onComplete: (List<RemoteUser>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        Log.d("Repository", "searchUsers: $query")
        Networking.githubApi.searchUsers(query).enqueue(
            object : Callback<SeverItemsWrapper<RemoteUser>> {
                override fun onResponse(
                    call: Call<SeverItemsWrapper<RemoteUser>>,
                    response: Response<SeverItemsWrapper<RemoteUser>>
                ) {
                    if (response.isSuccessful) {
                        onComplete(response.body()?.items.orEmpty())
                    } else {
                        onError(RuntimeException("incorrect status code"))
                    }
                }

                override fun onFailure(call: Call<SeverItemsWrapper<RemoteUser>>, t: Throwable) {
                    onError(t)
                }

            }
        )
    }

    fun getAuthenticatedUser(
        /*query: String,*/
        onComplete: (RemoteUser) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        Networking.githubApi.getAuthenticatedUser().enqueue(
            object : Callback<RemoteUser> {
                override fun onResponse(call: Call<RemoteUser>, response: Response<RemoteUser>) {
                    if (response.isSuccessful) {
                        response.body()?.let { onComplete(it) }
                    } else {
                        onError(java.lang.RuntimeException("incorrect status code"))
                    }
                }

                override fun onFailure(call: Call<RemoteUser>, t: Throwable) {
                    onError(t)
                }
            }
        )
    }
}