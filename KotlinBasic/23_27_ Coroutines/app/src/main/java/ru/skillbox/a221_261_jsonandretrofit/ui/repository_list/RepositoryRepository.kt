package ru.skillbox.a221_261_jsonandretrofit.ui.repository_list

import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.skillbox.a221_261_jsonandretrofit.data.RemoteRepository
import ru.skillbox.a221_261_jsonandretrofit.network.Networking
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class RepositoryRepository {

    suspend fun getAuthenticatedRepository(
    ): List<RemoteRepository> {
        return withContext(Dispatchers.IO) {
            suspendCoroutine<List<RemoteRepository>> { continuation ->
                val result = Networking.githubApi.getAuthenticatedRepos().execute()
                if (result.isSuccessful) {
                    continuation.resume(result.body().orEmpty())
                } else {
                    continuation.resumeWithException(RuntimeException("incorrect status code"))
                }
            }
        }
    }

    suspend fun checkRepoStared(
        owner: String,
        repo: String,
    ): Boolean {
        return suspendCancellableCoroutine { continuation ->
            continuation.invokeOnCancellation {
                Networking.githubApi.getRepoDetail(owner, repo).cancel()
            }
            continuation.isActive.let {
                Networking.githubApi.getRepoDetail(owner, repo).enqueue(
                    object : Callback<String> {
                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            if (response.isSuccessful) {
                                continuation.resume(response.code() == 204)
                            } else {
                                continuation.resumeWithException(RuntimeException("incorrect status code"))
                            }
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            continuation.resumeWithException(t)
                        }
                    }
                )
            }
        }
    }

    fun starRepo(
        owner: String,
        repo: String,
        onComplete: (Boolean) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        CoroutineScope(SupervisorJob() + Dispatchers.Main).launch {
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
    }


    fun unstarRepo(
        owner: String,
        repo: String,
        onComplete: (Boolean) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
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