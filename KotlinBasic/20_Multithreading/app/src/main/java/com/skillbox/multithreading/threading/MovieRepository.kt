package com.skillbox.multithreading.threading

import android.os.Handler
import android.os.Looper
import android.os.Process
import android.util.Log
import com.skillbox.multithreading.networking.Movie
import com.skillbox.multithreading.networking.Network
import com.skillbox.multithreading.networking.Network.MOVIE_API_KEY
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory
import java.util.concurrent.TimeUnit

class MovieRepository {

    val NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors()

    fun getMovieById(movieId: String): Movie? {
        return Network.api().getMovieById(movieId, MOVIE_API_KEY).execute()
            .body() //ignore exceptions
    }

    fun fetchMovies(
        movieIds: List<String>,
        movieIDsForMainThread: List<String>,
        onMoviesFetched: (movies: List<Movie>, fetchTime: Long) -> Unit
    ) {
        Log.d("ThreadTest", "BEFORE fetch on ${Thread.currentThread().name}")
        Thread {
            Log.d("ThreadTest", "fetchMovies start on ${Thread.currentThread().name}")
            val mainHandler = Handler(Looper.getMainLooper())

            val executor = Executors.newFixedThreadPool(NUMBER_OF_CORES)
            val allMovies = Collections.synchronizedList(mutableListOf<Movie>())

            val start = System.currentTimeMillis()
            val sizeOfChunk = movieIds.count() / NUMBER_OF_CORES
            Log.d("ThreadTest", "Size of chunks=$sizeOfChunk Count of cores=$NUMBER_OF_CORES")
            movieIds.chunked(sizeOfChunk).forEach {
                executor.submit {
                    allMovies.addAll(it.mapNotNull { movieId ->
                        Log.d(
                            "ThreadTest",
                            "executing on multiple threads ${Thread.currentThread().name}"
                        )
                        getMovieById(movieId)
                    })
                }
            }
            executor.shutdown()
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS)
            val requestTime = System.currentTimeMillis() - start

            mainHandler.post {
                Log.d(
                    "ThreadTest",
                    "after EXECUTOR fetchMovies continues on ${Thread.currentThread().name}"
                )
                val moviesFromMainThread =
                    movieIDsForMainThread.mapNotNull { movieId -> getMovieById(movieId) }
                allMovies.addAll(0, moviesFromMainThread)
                onMoviesFetched(allMovies, requestTime)
            }
        }.start()
    }

    private fun <T> createCallable(task: Callable<T>): Callable<T> {
        return Callable {
            try {
                task.call()
            } catch (e: Exception) {
                handle(e)
                throw e
            }
        }
    }

    private fun handle(e: Exception): Unit {
        println("got exception")
    }

    //val future2 = executor.submit<Int>(ReturnSomething())

    fun ReturnSomething(): Int {
        return 42
    }

    /** A ThreadFactory implementation which create new threads for the thread pool.
    The threads created is set to background priority, so it does not compete with the UI thread. **/
    private class BackgroundThreadFactory : ThreadFactory {
        override fun newThread(runnable: Runnable): Thread {
            val thread = Thread(runnable)
            thread.name = "CustomThread" + sTag
            thread.priority = Process.THREAD_PRIORITY_BACKGROUND

            // A exception handler is created to log the exception from threads
            thread.uncaughtExceptionHandler = Thread.UncaughtExceptionHandler { thread, ex ->
                Log.e(
                    Util.LOG_TAG,
                    thread.name + " encountered an error: " + ex.message
                )
            }
            return thread
        }

        companion object {
            private const val sTag = 1
        }
    }
}

////старое решение на потоках
/*        Thread {
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

        }.start()*/