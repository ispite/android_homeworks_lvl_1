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

class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding: FragmentMainBinding by viewBinding(FragmentMainBinding::bind)
    private val viewModel: MainViewModel by viewModels()

    private lateinit var textChangedFlow: Flow<String>
    private lateinit var radioGroupChangedFlow: Flow<MovieType>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        flowTextChanged()
        radioGroupChanged()
        viewModel.bind(textChangedFlow, radioGroupChangedFlow)
        binding.radioGroup.check(R.id.radioMovies)
//        bind()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.cancelJob()
    }

    private fun flowTextChanged() {
        viewLifecycleOwner.lifecycleScope.launch {
            textChangedFlow = binding.searchRequest.textChangedFlow()
        }
    }


    private fun radioGroupChanged() {
        viewLifecycleOwner.lifecycleScope.launch {
            radioGroupChangedFlow = binding.radioGroup.radioGroupChangedFlow()
                .map {
                    when (it) {
                        R.id.radioMovies -> MovieType.MOVIE
                        R.id.radioSeries -> MovieType.SERIES
                        else -> MovieType.EPISODE
                    }
                }
        }
    }

/*    private fun bind(queryFlow: Flow<String>, movieTypeFlow: Flow<MovieType>) {

    }*/

/*    private fun bindViewModel() {

    }*/
}