package ru.skillbox.a28_32_scopedstorage.presentation.main

import android.os.Bundle
import android.view.View
import ru.skillbox.a28_32_scopedstorage.databinding.FragmentMainBinding
import ru.skillbox.a28_32_scopedstorage.utils.ViewBindingFragment
import ru.skillbox.a28_32_scopedstorage.utils.autoCleared

class MainFragment : ViewBindingFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private var videosAdapter: VideosAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
    }

    private fun initList() {
        videosAdapter = VideosAdapter()
        with(binding.videosRecyclerView) {
            setHasFixedSize(true)
            adapter = videosAdapter
        }
    }
}