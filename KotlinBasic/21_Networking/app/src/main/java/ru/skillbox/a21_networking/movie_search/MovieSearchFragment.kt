package ru.skillbox.a21_networking.movie_search

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_movie_search.*
import ru.skillbox.a21_networking.R
import ru.skillbox.a21_networking.utils.autoCleared
import ru.skillbox.a21_networking.utils.isNotValidYear
import java.io.IOException

class MovieSearchFragment : Fragment(R.layout.fragment_movie_search) {

    private var movieAdapter: MovieSearchAdapter by autoCleared()

    private val viewModel: MovieSearchViewModel by viewModels()

    private val mainHandler = Handler(Looper.getMainLooper())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exposedMenu()
        initList()
        bindViewModel()
        //val a = "Puppy".removeFirstLastChar()
        //Log.d("fragment", "onViewCreated: $a")
    }

    private fun exposedMenu() {
        val cinematicTypes = resources.getStringArray(R.array.cinematic_types)
        val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, cinematicTypes)
        (menu.editText as? AutoCompleteTextView)?.setAdapter(adapter)
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
        /*val requestWithParameters = "Game of"
        mainHandler.post {
            viewModel.search(requestWithParameters)
        }*/

        var menuIndex: Long? = null
        autoCompleteTextView.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, position, l ->
                //val selectedItem = adapterView.getItemAtPosition(position).toString()
                menuIndex = adapterView.getItemIdAtPosition(position)
                //Log.d("ViewModel", "selectedItem: $selectedItem")
                Log.d("ViewModel", "selectedItem: $menuIndex")
            }

        buttonRequest.setOnClickListener {
            if (inputMovieYearEdit.isNotValidYear("The year not in range 1895..2022")) {
                val typeVideo = listOf("series", "movie", "episode", "")
                val queryTitle = inputMovieTitleEdit.text.toString()
                val queryYear = inputMovieYearEdit.text.toString()
                val queryType = typeVideo[menuIndex?.toInt() ?: 3]
                //Log.d("ViewModel", "bindViewModel: $queryYear")
                try {
                    viewModel.searchWithParameters(queryTitle, queryYear, queryType)
                } catch (e: IOException) {
                    //val error = viewModel.error
                    Log.d(
                        "ViewModel",
                        "bindViewModel: ${viewModel.error.value}",
                        viewModel.error.value
                    )
                }

            }
        }

        buttonResend.setOnClickListener {
            try {
                viewModel.resendRequest()
            } catch (e: IOException) {
                Log.d("ViewModel", "bindViewModel: ${viewModel.error.value}", viewModel.error.value)
            }

        }

        viewModel.isLoading.observe(viewLifecycleOwner, ::updateLoadingState)
        viewModel.movieList.observe(viewLifecycleOwner) {
            errorTextView.isVisible = false
            buttonResend.isVisible = false
            movieAdapter.items = it
        }
        viewModel.error.observe(viewLifecycleOwner) {
            errorTextView.isVisible = true
            buttonResend.isVisible = true
            errorTextView.text = it.toString()
        }
    }

    private fun updateLoadingState(isLoading: Boolean) {
        movieList.isVisible = isLoading.not()
        progressBar.isVisible = isLoading
        buttonRequest.isEnabled = isLoading.not()
    }
}