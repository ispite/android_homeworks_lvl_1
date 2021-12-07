package ru.skillbox.a21_networking.movie_search

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_movie_search.*
import kotlinx.android.synthetic.main.item_movie.*
import ru.skillbox.a21_networking.R
import ru.skillbox.a21_networking.utils.autoCleared

class MovieSearchFragment:Fragment(R.layout.fragment_movie_search) {

    private var movieAdapter: MovieSearchAdapter by autoCleared()

    private val viewModel: MovieSearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exposedMenu()
        initlist()
        bindViewModel()
    }

    fun exposedMenu() {
        val cinematicTypes = resources.getStringArray(R.array.cinematic_types)
        val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, cinematicTypes)
        (menu.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun initlist() {
        movieAdapter = MovieSearchAdapter()
        with(movieList) {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }
    }

    private fun bindViewModel() {
        val requestWithParameters = "Start"
        viewModel.search(requestWithParameters)
        viewModel.isLoading.observe(viewLifecycleOwner, ::updateLoadingState)
        viewModel.movieList.observe(viewLifecycleOwner) { movieAdapter.items = it }
    }

    private fun updateLoadingState(isLoading: Boolean) {
        movieList.isVisible = isLoading.not()
        progressBar.isVisible = isLoading

    }
}