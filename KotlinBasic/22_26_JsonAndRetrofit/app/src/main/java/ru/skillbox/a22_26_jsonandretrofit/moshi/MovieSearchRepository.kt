package ru.skillbox.a22_26_jsonandretrofit.moshi

import android.util.Log
import com.squareup.moshi.Moshi
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import ru.skillbox.a22_26_jsonandretrofit.network.Network
import java.io.IOException
import kotlin.random.Random

class MovieSearchRepository {

    fun searchMovie(
        title: String, callback: (List<Movie>) -> Unit,
        errorCallback: (IOException?) -> Unit
    ): Call {
        return Network.getSearchMovieCall(title).apply {
            enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    //Log.e("Server", "execute request error = ${e.message}", e)
                    Log.d("Repository", "onFailure: ${e.message}", e)
                    callback(emptyList())
                    errorCallback(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val responseString = response.body?.string().orEmpty()
                        val jsonObject = JSONObject(responseString)
                        Log.d("Repository", "onResponse: $responseString")
                        val movie = convertCustomMovieJsonToInstance(jsonObject.toString())
                        callback(movie)
                        errorCallback(null)
                    } else {
                        Log.d("Repository", "onResponse ELSE: ${response.message}")
                        callback(emptyList())
                        errorCallback(InvalidResponseException(response.message))
                    }
                }
            })
        }
    }

    private fun convertCustomMovieJsonToInstance(responseBodyString: String): List<Movie> {
        val moshi = Moshi.Builder()
            .add(MovieCustomAdapter())
            .build()

        val adapter = moshi.adapter(Movie::class.java).nonNull()

        return try {
            val movie = adapter.fromJson(responseBodyString)
            Log.d("Repository", "SUCCESS convertCustomMovieJsonToInstance: ${movie.toString()}")
            movie?.let { listOf(movie) } ?: emptyList()
        } catch (e: Exception) {
            Log.d("Repository", "EXCEPTION convertCustomMovieJsonToInstance: ${e.message}")
            emptyList()
        }
    }

    fun replaceScoreToMovie(movie: Movie): List<Movie> {
        movie.scores = mapOf("Your score:" to giveRandomScore().toString())
        return listOf(movie)
    }

    private fun giveRandomScore(): Double {
        val min = 0
        val max = 10
        val score = min + Random.nextDouble() * (max - min)
        return score.round(2)
    }

    fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return kotlin.math.round(this * multiplier) / multiplier
    }

    fun convertCustomMovieInstanceToJson(movie: Movie): String {
        val moshi = Moshi.Builder()
            .add(MovieCustomAdapter())
            .build()

        val adapter = moshi.adapter(Movie::class.java).nonNull()

        return try {
            val jsonMovie = adapter.toJson(movie)
            Log.d("Repository", "SUCCESS convertCustomMovieJsonToInstance: $jsonMovie")
            jsonMovie
        } catch (e: Exception) {
            Log.d("Repository", "EXCEPTION convertCustomMovieJsonToInstance: ${e.message}")
            ""
        }
    }
}