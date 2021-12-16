package ru.skillbox.a22_26_jsonandretrofit.moshi

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONException
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
                        /*val movies =*/ //parseMovieResponse(responseString)
                        val jsonObject = JSONObject(responseString)
                        Log.d("Repository", "onResponse: $responseString")
                        //val movieArray = jsonObject.getJSONArray("Search")
                        //Log.d("Repository", "onResponse: $responseString")
//                        if (response.networkResponse?.body != null) {
                            val movie= convertCustomMovieJsonToInstance(jsonObject.toString())
                            callback(movie)
//                            errorCallback(null)
//                            }

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

    private fun convertSimpleMovieJsonToInstance(responseBodyString: String): List<Movie> {
        val moshi = Moshi.Builder().build()

        val adapter = moshi.adapter(Movie::class.java).nonNull()

        return try {
            val movie = adapter.fromJson(responseBodyString)
            Log.d("Repository", "convertSimpleMovieJsonToInstance: ${movie.toString()}")
            movie?.let { listOf(movie) } ?: emptyList()
            //textView.text = movie.toString()
        } catch (e: Exception) {
            //textView.text = "parse error = ${e.message}"
            Log.d("Repository", "convertSimpleMovieJsonToInstance: ${e.message}")
            emptyList()
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
            //textView.text = movie.toString()

        } catch (e: Exception) {
            //textView.text = "parse error = ${e.message}"
            Log.d("Repository", "EXCEPTION convertCustomMovieJsonToInstance: ${e.message}")
            emptyList()
        }

    }

    private fun convertMovieListJsonToInstance(responseBodyString: String) {
        val moshi = Moshi.Builder().build()

        val movieListType = Types.newParameterizedType(
            List::class.java,
            Movie::class.java
        )

        val adapter = moshi.adapter<List<Movie>>(movieListType).nonNull()

        try {
            val movies = adapter.fromJson(responseBodyString)
            Log.d("Repository", "convertMovieListJsonToInstance: ${movies?.get(0).toString()}")
            //textView.text = movies.toString()
        } catch (e: Exception) {
            //textView.text = "parse error = ${e.message}"
            Log.d("Repository", "convertMovieListJsonToInstance: ${e.message}")
        }

    }

    fun replaceScoreToMovie(movie: Movie):List<Movie> {
        movie.scores = mapOf("Your score:" to giveRandomScore().toString())
        return listOf(movie)
    }

    private fun giveRandomScore():Double {
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
            //val movie = adapter.fromJson(responseBodyString)
            Log.d("Repository", "SUCCESS convertCustomMovieJsonToInstance: $jsonMovie")
            //movie?.let { listOf(movie) } ?: emptyList()
            //textView.text = movie.toString()
            jsonMovie
        } catch (e: Exception) {
            //textView.text = "parse error = ${e.message}"
            Log.d("Repository", "EXCEPTION convertCustomMovieJsonToInstance: ${e.message}")
            ""
        }

    }


}