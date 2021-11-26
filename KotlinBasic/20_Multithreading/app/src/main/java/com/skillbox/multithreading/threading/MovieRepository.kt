package com.skillbox.multithreading.threading

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.skillbox.multithreading.networking.Movie
import com.skillbox.multithreading.networking.Network
import com.skillbox.multithreading.networking.Network.MOVIE_API_KEY
import java.util.*

class MovieRepository {

    fun getMovieById(movieId: String): Movie? {
        return Network.api().getMovieById(movieId, MOVIE_API_KEY).execute()
            .body() //ignore exceptions
    }

    fun fetchMovies(
        movieIds: List<String>,
        movieIDsForMainThread: List<String>,
        onMoviesFetched: (movies: List<Movie>, fetchTime: Long) -> Unit
    ) {
        Log.d("ThreadTest", "fetchMovies start on ${Thread.currentThread().name}")
        val mainHandler = Handler(Looper.getMainLooper())

        Thread {
            val startTime = System.currentTimeMillis()
            val allMovies = Collections.synchronizedList(mutableListOf<Movie>())
            Log.d("ThreadTest", "fetchMovies continues on ${Thread.currentThread().name}")
            val threads = movieIds.chunked(1).map { movieChunk ->
                Thread {
                    val movies = movieChunk.mapNotNull { movieId ->
                        getMovieById(movieId)
                    }
                    allMovies.addAll(movies)
                }
            }

            threads.forEach { it.start() }
            threads.forEach { it.join() }

            val requestTime = System.currentTimeMillis() - startTime

            mainHandler.post {
                Log.d("ThreadTest", "fetchMovies continues on ${Thread.currentThread().name}")
                val moviesFromMainThread =
                    movieIDsForMainThread.mapNotNull { //movieId -> getMovieById(movieId)
                           movieId -> Network.api().getMovieById(movieId, "43541a05").execute().body() }
                allMovies.addAll(0, moviesFromMainThread)
                //allMovies = moviesFromMainThread + allMovies
                onMoviesFetched(allMovies, requestTime)
            }

        }.start()

        Log.d("ThreadTest", "fetchMovies end on ${Thread.currentThread().name}")
    }
}