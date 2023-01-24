package ru.skillbox.a34_38_Release_Flow

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import ru.skillbox.a34_38_Release_Flow.databinding.FragmentDbListBinding
import ru.skillbox.a34_38_Release_Flow.utils.autoCleared

class DbListFragment : Fragment(R.layout.fragment_db_list) {

    private val binding: FragmentDbListBinding by viewBinding(FragmentDbListBinding::bind)
    private var videoAdapter: VideoAdapter by autoCleared()
    private val viewModel: DbListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        bindViewModel()
//        viewModel.observeVideos()
    }

    private fun initList() {
        videoAdapter = VideoAdapter()
        with(binding.dbListRecyclerView) {
            adapter = videoAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun bindViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.videoListFlow.collect { videoAdapter.submitList(it) }
            }
        }
    }
}