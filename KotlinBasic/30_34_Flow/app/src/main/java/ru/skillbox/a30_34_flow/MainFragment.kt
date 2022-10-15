package ru.skillbox.a30_34_flow

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.skillbox.a30_34_flow.data.MovieType
import ru.skillbox.a30_34_flow.databinding.FragmentMainBinding
import ru.skillbox.a30_34_flow.utils.radioGroupChangedFlow
import ru.skillbox.a30_34_flow.utils.textChangedFlow
import timber.log.Timber

class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding: FragmentMainBinding by viewBinding(FragmentMainBinding::bind)
    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        flowTextChanged()
        radioGroupChanged()
//        bind()
    }

    private fun flowTextChanged() {
        viewLifecycleOwner.lifecycleScope.launch {
            binding.searchRequest.textChangedFlow().collect {
                Timber.d(it)
                viewModel.editTextFlow.postValue(it)
            }
        }
    }

    private fun radioGroupChanged() {
        viewLifecycleOwner.lifecycleScope.launch {
            binding.radioGroup.radioGroupChangedFlow()
                .map {
                    when (it) {
                        R.id.radioMovies -> MovieType.MOVIE
                        R.id.radioSeries -> MovieType.SERIES
                        else -> MovieType.EPISODE
                    }
                }
                .collect {
//                    return@launch it
                    Timber.d(it.toString())
                    viewModel.radioGroupFlow.postValue(it)
                }
        }
    }

    private fun bind(queryFlow: Flow<String>, movieTypeFlow: Flow<MovieType>) {

    }

/*    private fun bindViewModel() {

    }*/
}