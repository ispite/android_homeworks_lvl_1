package ru.skillbox.a30_34_flow

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.skillbox.a30_34_flow.data.MovieType
import ru.skillbox.a30_34_flow.databinding.FragmentMainBinding
import ru.skillbox.a30_34_flow.utils.autoCleared
import ru.skillbox.a30_34_flow.utils.radioGroupChangedFlow
import ru.skillbox.a30_34_flow.utils.textChangedFlow

class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding: FragmentMainBinding by viewBinding(FragmentMainBinding::bind)
    private val viewModel: MainViewModel by viewModels()
    private var videoAdapter: VideoAdapter by autoCleared()

    private lateinit var textChangedFlow: Flow<String>
    private lateinit var radioGroupChangedFlow: Flow<MovieType>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        flowTextChanged()
        radioGroupChanged()
        viewModel.bind(textChangedFlow, radioGroupChangedFlow)
        binding.radioGroup.check(R.id.radioMovies)
        bindViewModel()
        bind()
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

    private fun initList() {
        videoAdapter = VideoAdapter()
        with(binding.videosRecyclerView) {
            adapter = videoAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun bindViewModel() {
        viewModel.videoList.observe(viewLifecycleOwner) {
            videoAdapter.submitList(it)
        }
    }

    private fun bind() {
        binding.toDbListButton.setOnClickListener { findNavController().navigate(R.id.action_mainFragment_to_dbListFragment) }
    }

}