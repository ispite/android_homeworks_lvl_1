package ru.skillbox.a28_32_scopedstorage.presentation.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.skillbox.a28_32_scopedstorage.databinding.FragmentMainBinding
import ru.skillbox.a28_32_scopedstorage.utils.ViewBindingFragment
import ru.skillbox.a28_32_scopedstorage.utils.autoCleared
import ru.skillbox.a28_32_scopedstorage.utils.haveQ
import ru.skillbox.a28_32_scopedstorage.utils.toast
import timber.log.Timber

class MainFragment : ViewBindingFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private var videosAdapter: VideosAdapter by autoCleared()
    private val viewModel: VideoListViewModel by viewModels()

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")
        initPermissionResultListener()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated")
        initList()
        bindViewModel()
        if (hasPermission().not()) {
            requestPermissions()
        }
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume")
        viewModel.updatePermissionState(hasPermission())
    }

    private fun bindViewModel() {
        viewModel.toast.observe(viewLifecycleOwner) { toast(it) }
        viewModel.videoList.observe(viewLifecycleOwner) {
            Timber.d("videoList $it")
            videosAdapter.submitList(it) }
    }

    private fun hasPermission(): Boolean {
        return PERMISSIONS.all {
            ActivityCompat.checkSelfPermission(
                requireContext(),
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun initPermissionResultListener() {
        Timber.d("initPermissionResultListener")
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionToGrantedMap: Map<String, Boolean> ->
            if (permissionToGrantedMap.values.all { it }) {
                Timber.d("permissionGranted")
                viewModel.permissionGranted()
            } else {
                Timber.d("permissionDenied")
                viewModel.permissionDenied()
            }
        }
    }

    private fun initList() {
        Timber.d("initList")
        videosAdapter = VideosAdapter()
        with(binding.videosRecyclerView) {
            adapter = videosAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun requestPermissions() {
        requestPermissionLauncher.launch(*PERMISSIONS.toTypedArray())
    }

    companion object {
        private val PERMISSIONS = listOfNotNull(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
                .takeIf { haveQ() }
        )
    }
}