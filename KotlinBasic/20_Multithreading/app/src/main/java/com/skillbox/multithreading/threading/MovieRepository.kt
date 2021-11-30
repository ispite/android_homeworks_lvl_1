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

class MovieRepository {

    val NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors()
    var executor = Executors.newFixedThreadPool(NUMBER_OF_CORES/*, BackgroundThreadFactory()*/)

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

        val future = executor.submit<List<Any>>() {
            val requestTime: Long
            val startTime = System.currentTimeMillis()
            var allMovies = Collections.synchronizedList(mutableListOf<Movie>())
            Log.d("ThreadTest", "EXECUTOR fetchMovies continues on ${Thread.currentThread().name}")

            val callable1 = Callable<MutableList<Movie>> {
                Log.d(
                    "ThreadTest",
                    "Callable start fetchMovies continues on ${Thread.currentThread().name}"
                )

                allMovies = movieIds.chunked(NUMBER_OF_CORES).map { movieChunk ->
                    val movies = movieChunk.mapNotNull { movieId ->
                        getMovieById(movieId)
                    }
                    Log.d("ThreadTest", "Callable fetching movies ${Thread.currentThread().name}")
                    movies
                }.flatten()

                //requestTime = System.currentTimeMillis() - startTime
                allMovies
            }
            val c1 = createCallable(callable1)
            Log.d("ThreadTest", "callable1 =  on ${Thread.currentThread().name}")

            requestTime = System.currentTimeMillis() - startTime
            return@submit listOf<Any>(c1.call(), requestTime)
        }

        mainHandler.post {
            Log.d(
                "ThreadTest",
                "after EXECUTOR fetchMovies continues on ${Thread.currentThread().name}"
            )
            val moviesFromMainThread =
                movieIDsForMainThread.mapNotNull { movieId ->
                    getMovieById(movieId)
                }
            val result = future.get()
            val allMovies: MutableList<Movie> = result[0] as MutableList<Movie>
            val requestTime: Long = result[1] as Long
            allMovies.addAll(0, moviesFromMainThread)
            onMoviesFetched(allMovies, requestTime)
        }

        Log.d("ThreadTest", "fetchMovies end on ${Thread.currentThread().name}")
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