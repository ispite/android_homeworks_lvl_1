package com.skillbox.multithreading.threading

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.multithreading.MovieRecyclerViewAdapter
import com.skillbox.multithreading.R
import kotlinx.android.synthetic.main.fragment_threading.*
import java.util.concurrent.Executors

class ThreadingFragment : Fragment(R.layout.fragment_threading) {

    private val TAG = "MainActivity"
    private val viewModel: ThreadingViewModel by viewModels()
    //lateinit var viewModel2: ThreadingViewModel
    //private var movieAdapter by AutoClearedValue<MovieRecyclerViewAdapter>(this)
    private lateinit var handler: Handler
    private val mainHandler = Handler(Looper.getMainLooper())

    private var mCustomThreadPoolManager: CustomThreadPoolManager? = null

    //val NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors()
    //var executor = Executors.newFixedThreadPool(NUMBER_OF_CORES)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initAdapter()


        requestMovies.setOnClickListener {
            viewModel.requestMovies()
        }
        viewModel.time.observe(viewLifecycleOwner) {
            Log.d("ThreadTest", "livedata changed on ${Thread.currentThread().name}")
            timeTextView.text = it.toString()
        }
        // get the thread pool manager instance
        mCustomThreadPoolManager = CustomThreadPoolManager.getsInstance()
        // CustomThreadPoolManager stores activity as a weak reference. No need to unregister.
        //mCustomThreadPoolManager!!.setUiThreadCallback(this)

/*        executor.execute() {

        }*/
    }

    private fun initAdapter() {

        //viewModel2 = ViewModelProvider(this).get(ThreadingViewModel()::class.java)
        val myAdapter = MovieRecyclerViewAdapter()
        with(threadingRecyclerView) {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel.moviesList.observe(viewLifecycleOwner) {
            Log.d(
                TAG,
                "initAdapter onCreate: $it"
            )
            myAdapter.setMovieList(it)
        }

        val backGroundThread = HandlerThread("Handler thread").apply {
            start()
        }
        handler = Handler(backGroundThread.looper)
        mainHandler.postDelayed({
        ///////////////не получается вывести в SingleLiveEvent, т.к. получается ошибка: Cannot invoke observe on a background thread
            viewModel.showToast.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), "Список обновлён", Toast.LENGTH_SHORT).show()
            }
            //Toast.makeText(requireContext(), "Список обновлён", Toast.LENGTH_SHORT).show()
        }, 1000)

    }

}

