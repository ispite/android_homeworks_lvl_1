package ru.skillbox.a221_261_jsonandretrofit.ui.main

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.skillbox.a221_261_jsonandretrofit.data.RemoteBio
import ru.skillbox.a221_261_jsonandretrofit.data.RemoteUser
import ru.skillbox.a221_261_jsonandretrofit.network.Networking

class MainRepository {

    fun changeBio(
        bioFromTextInput: String,
        onComplete: (String) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val localBio = RemoteBio(bio = bioFromTextInput)

        Networking.githubApi.changeBio(localBio).enqueue(
            object : Callback<RemoteUser> {
                override fun onResponse(call: Call<RemoteUser>, response: Response<RemoteUser>) {
                    if (response.isSuccessful) {
                        //Log.d("Patch", "onResponse: ${response.body()}")
                        onComplete(response.body()?.bio.orEmpty())
                    } else {
                        onError(RuntimeException("incorrect status code"))
                    }
                }

                override fun onFailure(call: Call<RemoteUser>, t: Throwable) {
                    onError(t)
                }
            }
        )
    }
}