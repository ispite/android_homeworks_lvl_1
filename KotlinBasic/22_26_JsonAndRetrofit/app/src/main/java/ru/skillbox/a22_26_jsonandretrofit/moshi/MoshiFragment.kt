package ru.skillbox.a22_26_jsonandretrofit.moshi

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_moshi.*
import ru.skillbox.a22_26_jsonandretrofit.R
import ru.skillbox.a22_26_jsonandretrofit.utils.autoCleared
import java.io.IOException

class MoshiFragment : Fragment(R.layout.fragment_moshi) {

    private var movieAdapter: MovieSearchAdapter by autoCleared()
    private val viewModel: MovieSearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        bindViewModel()
    }

    private fun initList() {
        movieAdapter = MovieSearchAdapter()
        with(movieList) {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun bindViewModel() {
        buttonFind.setOnClickListener {
            val queryTitle = inputMovieTitleEdit.text.toString()
            try {
                viewModel.searchWithParameters(queryTitle)
            } catch (e: IOException) {
                Log.d(
                    "ViewModel",
                    "bindViewModel: ${viewModel.error.value}",
                    viewModel.error.value
                )
            }
        }

        buttonScore.setOnClickListener {
            movieAdapter.setEmptyList()
            viewModel.replaceScoreToMovie()
            viewModel.convertMovieToJson()
            Log.d("Fragment", "bindViewModel: ${viewModel.publicJsonLiveData.value}")
        }
        viewModel.isLoading.observe(viewLifecycleOwner, ::updateLoadingState)
        viewModel.movieViewModel.observe(viewLifecycleOwner) {
            movieAdapter.items = it
            buttonScore.isEnabled = viewModel.movieViewModel.value!!.isNotEmpty()
        }
    }

    private fun updateLoadingState(isLoading: Boolean) {
        movieList.isVisible = isLoading.not()
        buttonFind.isEnabled = isLoading.not()
    }
}