package ru.skillbox.a221_261_jsonandretrofit.ui.repository_list

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.skillbox.a221_261_jsonandretrofit.data.RemoteRepository
import ru.skillbox.a221_261_jsonandretrofit.network.Networking

class RepositoryRepository {

    fun getAuthenticatedRepository(
        onComplete: (List<RemoteRepository>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        Log.d("Repository", "REQUEST SEND")
        Networking.githubApi.getAuthenticatedRepos().enqueue(
            object : Callback<List<RemoteRepository>> {
                override fun onResponse(
                    call: Call<List<RemoteRepository>>,
                    response: Response<List<RemoteRepository>>
                ) {
                    if (response.isSuccessful) {
                        onComplete(response.body().orEmpty())
                    } else {
                        onError(RuntimeException("incorrect status code"))
                    }
                }

                override fun onFailure(
                    call: Call<List<RemoteRepository>>,
                    t: Throwable
                ) {
                    onError(t)
                }
            }
        )
    }

    fun checkRepoStared(
        owner: String,
        repo: String,
        onComplete: (Boolean) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        Networking.githubApi.getRepoDetail(owner, repo).enqueue(
            object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        onComplete(response.code() == 204)
                    } else {
                        onError(RuntimeException("incorrect status code"))
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    onError(t)
                }
            }
        )
    }

    fun starRepo(
        owner: String,
        repo: String,
        onComplete: (Boolean) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        Networking.githubApi.starRepo(owner, repo).enqueue(
            object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        onComplete(response.code() == 204)
                    } else {
                        onError(RuntimeException("incorrect status code"))
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    onError(t)
                }
            }
        )
    }

    fun unstarRepo(
        owner: String,
        repo: String,
        onComplete: (Boolean) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        Networking.githubApi.unstarRepo(owner, repo).enqueue(
            object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        onComplete(response.code() == 204)
                    } else {
                        onError(RuntimeException("incorrect status code"))
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    onError(t)
                }
            }
        )
    }

    fun getStarredRepositories(
        username: String,
        onComplete: (List<RemoteRepository>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        Networking.githubApi.getStarredRepos(username).enqueue(
            object : Callback<List<RemoteRepository>> {
                override fun onResponse(
                    call: Call<List<RemoteRepository>>,
                    response: Response<List<RemoteRepository>>
                ) {
                    if (response.isSuccessful) {
                        onComplete(response.body().orEmpty())
                    } else {
                        onError(RuntimeException("incorrect status code"))
                    }
                }

                override fun onFailure(call: Call<List<RemoteRepository>>, t: Throwable) {
                    onError(t)
                }

            }
        )

    }
}