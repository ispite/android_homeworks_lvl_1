package ru.skillbox.a22_26_jsonandretrofit.moshi

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.android.synthetic.main.fragment_moshi.*
import ru.skillbox.a22_26_jsonandretrofit.R
import ru.skillbox.a22_26_jsonandretrofit.utils.autoCleared
import java.io.IOException

class MoshiFragment:Fragment(R.layout.fragment_moshi) {

    private var movieAdapter: MovieSearchAdapter by autoCleared()

    private val viewModel: MovieSearchViewModel by viewModels()

    private val simpleMovie = """
        {
            "title": "Star Wars: Episode IV - A New Hope",
            "year": 1977,
            "imdb_id": "tt0076759"
        }
    """

    private val movieListList = """
        [
            {
                "title": "Star Wars: Episode IV - A New Hope",
                "year": 1977,
                "rating": "PG",
                "imdb_id": "tt0076759"
            },
            {
                "title": "The Lord of the Rings: The Fellowship of the Ring",
                "year": 2001,
                "rating": "G",
                "imdb_id": "tt0120737"
            }
        ]
    """

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
/*        buttonFind.setOnClickListener {
            convertSimpleMovieJsonToInstance()
        }*/
        initList()
        bindViewModel()
        //convertMovieListJsonToInstance()
    }

    private fun convertSimpleMovieJsonToInstance() {
        val moshi = Moshi.Builder().build()

        val adapter = moshi.adapter(Movie::class.java).nonNull()

        try {
            val movie = adapter.fromJson(simpleMovie)
            textView.text = movie.toString()
        } catch (e: Exception) {
            textView.text = "parse error = ${e.message}"
        }
    }

    private fun convertMovieListJsonToInstance() {
        val moshi = Moshi.Builder().build()

        val movieListType = Types.newParameterizedType(
            List::class.java,
            Movie::class.java
        )

        val adapter = moshi.adapter<List<Movie>>(movieListType).nonNull()

        try {
            val movies = adapter.fromJson(movieListList)
            textView.text = movies.toString()
        } catch (e: Exception) {
            textView.text = "parse error = ${e.message}"
        }

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

/*        var menuIndex: Long? = null
        autoCompleteTextView.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, position, l ->
                //val selectedItem = adapterView.getItemAtPosition(position).toString()
                menuIndex = adapterView.getItemIdAtPosition(position)
                //Log.d("ViewModel", "selectedItem: $selectedItem")
                Log.d("ViewModel", "selectedItem: $menuIndex")
            }*/

        buttonFind.setOnClickListener {

//                val typeVideo = listOf("series", "movie", "episode", "")
                val queryTitle = inputMovieTitleEdit.text.toString()
//                val queryYear = inputMovieYearEdit.text.toString()
//                val queryType = typeVideo[menuIndex?.toInt() ?: 3]
                //Log.d("ViewModel", "bindViewModel: $queryYear")
                try {
                    viewModel.searchWithParameters(queryTitle)
                } catch (e: IOException) {
                    //val error = viewModel.error
                    Log.d(
                        "ViewModel",
                        "bindViewModel: ${viewModel.error.value}",
                        viewModel.error.value
                    )
                }


        }

/*        buttonResend.setOnClickListener {
            try {
                viewModel.resendRequest()
            } catch (e: IOException) {
                Log.d("ViewModel", "bindViewModel: ${viewModel.error.value}", viewModel.error.value)
            }

        }*/

        viewModel.isLoading.observe(viewLifecycleOwner, ::updateLoadingState)
        viewModel.movieViewModel.observe(viewLifecycleOwner) {
//            errorTextView.isVisible = false
//            buttonResend.isVisible = false
            movieAdapter.items = listOf(it)

        }
/*        viewModel.error.observe(viewLifecycleOwner) {
            if (it != null) {
//                errorTextView.isVisible = true
//                buttonResend.isVisible = true
                errorTextView.text = it.toString()
            }

        }*/
    }

    private fun updateLoadingState(isLoading: Boolean) {
        movieList.isVisible = isLoading.not()
        //progressBar.isVisible = isLoading
        buttonFind.isEnabled = isLoading.not()
    }
}