package com.skillbox.multithreading.threading

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.multithreading.AutoClearedValue
import com.skillbox.multithreading.MovieRecyclerViewAdapter
import com.skillbox.multithreading.R
import kotlinx.android.synthetic.main.fragment_threading.*

class ThreadingFragment : Fragment(R.layout.fragment_threading) {

    private val TAG = "MainActivity"
    //private val viewModel: ThreadingViewModel by viewModels()
    lateinit var viewModel2: ThreadingViewModel
    private var movieAdapter by AutoClearedValue<MovieRecyclerViewAdapter>(this)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initAdapter()

        requestMovies.setOnClickListener {
            viewModel2.requestMovies()
        }
        viewModel2.time.observe(viewLifecycleOwner) {
            Log.d("ThreadTest", "livedata changed on ${Thread.currentThread().name}")
            timeTextView.text = it.toString()
        }
//        viewModel.movies.observe(viewLifecycleOwner) { moviesTextView.text = it }
//        val factory = ThreadingViewModelFactory()
//        initAdapter()
//        viewModel.moviesList.observe(viewLifecycleOwner) { moviesList -> movieAdapter. }
//        var viewManager = LinearLayoutManager(requireContext())

    }

    private fun initAdapter() {
//        movieAdapter = MovieRecyclerViewAdapter()
/*        val threadingRecyclerViewMember = threadingRecyclerView
        val application = requireNotNull(requireActivity()).application
        val factory = ThreadingViewModelFactory()
        viewModel = ViewModelProviders
        with(threadingRecyclerView) {
            adapter = movieAdapter
            layoutManager =LinearLayoutManager(requireContext())
//            setHasFixedSize(true)
        }
        viewModel.moviesList.observe(viewLifecycleOwner) { threadingRecyclerView.adapter = MovieRecyclerViewAdapter(viewModel, it, requireContext() ) }
    }*/
        //viewModel2 = ViewModelProvider(this, ThreadingViewModelFactory().ge)
        viewModel2 = ViewModelProvider(this).get(ThreadingViewModel()::class.java)
        val myAdapter = MovieRecyclerViewAdapter()
//        threadingRecyclerView.adapter = myAdapter

        with(threadingRecyclerView) {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel2.moviesList.observe(viewLifecycleOwner) {
            Log.d(
                TAG,
                "initAdapter onCreate: $it"
            )
            myAdapter.setMovieList(it)
        }

    }

}

