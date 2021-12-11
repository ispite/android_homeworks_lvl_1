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

class ThreadingFragment : Fragment(R.layout.fragment_threading) {

    private val TAG = "MainActivity"
    private val viewModel: ThreadingViewModel by viewModels()
    private lateinit var handler: Handler
    private val mainHandler = Handler(Looper.getMainLooper())

    private var mCustomThreadPoolManager: CustomThreadPoolManager? = null

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

        swipeRefresh.setOnRefreshListener {
            Log.d("ThreadTest", "Refresh started on ${Thread.currentThread().name}")
            viewModel.requestMovies()
            swipeRefresh.isRefreshing = false
        }
    }

    private fun initAdapter() {

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
            viewModel.showToast.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), "Список обновлён", Toast.LENGTH_SHORT).show()
            }
        }, 1000)

    }

}

