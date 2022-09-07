package ru.skillbox.a28_32_scopedstorage.presentation.main

import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.skillbox.a28_32_scopedstorage.databinding.FragmentMainBinding
import ru.skillbox.a28_32_scopedstorage.utils.ViewBindingFragment
import ru.skillbox.a28_32_scopedstorage.utils.autoCleared

class MainFragment : ViewBindingFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private var videosAdapter: VideosAdapter by autoCleared()
    private val viewModel: VideoListViewModel by viewModels()

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPermissionResultListener()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
    }

    private fun initPermissionResultListener() {
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionToGrantedMap: Map<String, Boolean> ->
            if (permissionToGrantedMap.values.all { it }) {
//                viewModel
            }
        }
    }

    private fun initList() {
        videosAdapter = VideosAdapter()
        with(binding.videosRecyclerView) {
            adapter = videosAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }
}