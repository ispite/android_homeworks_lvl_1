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

class MovieSearchRepository {

    fun searchMovie(
        title: String, callback: (Movie?) -> Unit,
        errorCallback: (IOException?) -> Unit
    ): Call {
        return Network.getSearchMovieCall(title).apply {
            enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    //Log.e("Server", "execute request error = ${e.message}", e)
                    callback(null)
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
                        val movie= convertCustomMovieJsonToInstance(jsonObject.toString())

                        callback(movie)
                        errorCallback(null)
                    } else {
                        callback(null)
                        errorCallback(InvalidResponseException(response.message))
                    }
                }

            })
        }
    }

    private fun convertSimpleMovieJsonToInstance(responseBodyString: String): Movie? {
        val moshi = Moshi.Builder().build()

        val adapter = moshi.adapter(Movie::class.java).nonNull()

        return try {
            val movie = adapter.fromJson(responseBodyString)
            Log.d("Repository", "convertSimpleMovieJsonToInstance: ${movie.toString()}")
            movie
            //textView.text = movie.toString()

        } catch (e: Exception) {
            //textView.text = "parse error = ${e.message}"
            Log.d("Repository", "convertSimpleMovieJsonToInstance: ${e.message}")
            null
        }

    }

    private fun convertCustomMovieJsonToInstance(responseBodyString: String): Movie? {
        val moshi = Moshi.Builder()
            .add(MovieCustomAdapter())
            .build()

        val adapter = moshi.adapter(Movie::class.java).nonNull()

        return try {
            val movie = adapter.fromJson(responseBodyString)
            Log.d("Repository", "convertSimpleMovieJsonToInstance: ${movie.toString()}")
            movie
            //textView.text = movie.toString()

        } catch (e: Exception) {
            //textView.text = "parse error = ${e.message}"
            Log.d("Repository", "convertSimpleMovieJsonToInstance: ${e.message}")
            null
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

    private fun parseMovieResponse(responseBodyString: String)/*: List<RemoteMovie>*/ {
        /*return*/ try {
            val jsonObject = JSONObject(responseBodyString)
            val movieArray = jsonObject.getJSONArray("Search")
            Log.d("Repository", "parseMovieResponse: $jsonObject")
            (0 until movieArray.length()).map { index -> movieArray.getJSONObject(index) }
                .map { movieJsonObject ->

                    Log.d("Repository", "parseMovieResponse: $movieJsonObject")
/*                    val title = movieJsonObject.getString("Title")
                    val year = movieJsonObject.getString("Year")
                    val id = movieJsonObject.getString("imdbID")
                    val type = movieJsonObject.getString("Type")
                    val poster = movieJsonObject.getString("Poster")
                    RemoteMovie(id = id, title = title, type = type, year = year, poster = poster)*/
                }
        } catch (e: JSONException) {
            Log.e("Server", "parse response error = ${e.message}", e)
            emptyList()
        }
    }

}