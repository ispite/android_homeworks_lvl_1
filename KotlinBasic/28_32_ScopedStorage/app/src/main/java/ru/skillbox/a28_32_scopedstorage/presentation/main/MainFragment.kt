package ru.skillbox.a28_32_scopedstorage.presentation.main

import android.Manifest
import android.app.Activity
import android.app.RemoteAction
import android.content.IntentSender
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.skillbox.a28_32_scopedstorage.R
import ru.skillbox.a28_32_scopedstorage.databinding.FragmentMainBinding
import ru.skillbox.a28_32_scopedstorage.utils.ViewBindingFragment
import ru.skillbox.a28_32_scopedstorage.utils.autoCleared
import ru.skillbox.a28_32_scopedstorage.utils.haveQ
import ru.skillbox.a28_32_scopedstorage.utils.toast

/**
 * Code used with [IntentSender] to request user permission to delete an image with scoped storage.
 */
private const val DELETE_PERMISSION_REQUEST = 0x1033

class MainFragment : ViewBindingFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private var videosAdapter: VideosAdapter by autoCleared()
    private val viewModel: VideoListViewModel by viewModels()

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var recoverableActionLauncher: ActivityResultLauncher<IntentSenderRequest>

    //    private lateinit var selectDirectoryLauncher: ActivityResultLauncher<Uri?>
    private lateinit var createVideoLauncher: ActivityResultLauncher<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPermissionResultListener()
        initRecoverableActionListener()
//        initSelectDirectory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        initCallback()
        bindViewModel()
        if (hasPermission().not()) {
            requestPermissions()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.updatePermissionState(hasPermission())
    }

    private fun initCallback() {
        binding.downloadFab.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToDownloadFragment())
        }
/*        binding.selectDirectoryFab.setOnClickListener {
            selectDirectory()
        }*/
    }

    private fun bindViewModel() {
        viewModel.toast.observe(viewLifecycleOwner) { toast(it) }
        viewModel.videoList.observe(viewLifecycleOwner) {
            videosAdapter.submitList(it)
        }
        viewModel.recoverableAction.observe(viewLifecycleOwner, ::handleRecoverableAction)
        viewModel.permissionNeededForDelete.observe(viewLifecycleOwner, ::handleMoveToTrash)
        viewModel.permissionNeededForFavorite.observe(viewLifecycleOwner, ::handleMarkAsFavorite)
    }

    private fun hasPermission(): Boolean {
        return PERMISSIONS.all {
            val result = ActivityCompat.checkSelfPermission(
                requireContext(),
                it
            ) == PackageManager.PERMISSION_GRANTED
            result
        }
    }

    private fun initPermissionResultListener() {
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionToGrantedMap: Map<String, Boolean> ->
            permissionToGrantedMap.values.all {
                true
            }
            if (permissionToGrantedMap.values.all { it }) {
                viewModel.permissionGranted()
            } else {
                viewModel.permissionDenied()
            }
        }
    }

    private fun initList() {
        videosAdapter =
            VideosAdapter(viewModel::deleteVideo, viewModel::moveToTrash, viewModel::markAsFavorite)
        with(binding.videosRecyclerView) {
            adapter = videosAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun initRecoverableActionListener() {
        recoverableActionLauncher = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { activityResult ->
            val isConfirmed = activityResult.resultCode == Activity.RESULT_OK
            if (isConfirmed) {
                viewModel.confirmDelete()
            } else {
                viewModel.declineDelete()
            }
        }
    }

/*    private fun initSelectDirectory() {
        selectDirectoryLauncher = registerForActivityResult(
            ActivityResultContracts.OpenDocumentTree()
        ) { uri ->
            handleSelectDirectory(uri)
        }
    }*/

    private fun initCreateVideoLauncher() {
        createVideoLauncher = registerForActivityResult(
            ActivityResultContracts.CreateDocument("video/*")
        ) { uri ->

        }
    }

    private fun requestPermissions() {
        requestPermissionLauncher.launch(*PERMISSIONS.toTypedArray())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleRecoverableAction(action: RemoteAction) {
        val request = IntentSenderRequest.Builder(action.actionIntent.intentSender)
            .build()
        recoverableActionLauncher.launch(request)
    }

    private fun handleMoveToTrash(intentSender: IntentSender) {
        val intentSenderRequest = IntentSenderRequest.Builder(intentSender).build()
        recoverableActionLauncher.launch(intentSenderRequest)
    }

    private fun handleMarkAsFavorite(intentSender: IntentSender) {
        val intentSenderRequest = IntentSenderRequest.Builder(intentSender).build()
        recoverableActionLauncher.launch(intentSenderRequest)
    }

/*    private fun selectDirectory() {
        selectDirectoryLauncher.launch(null)
    }*/

    private fun handleSelectDirectory(uri: Uri?) {
        if (uri == null) {
            toast(R.string.select_directory_error_toast_message)
            return
        }
//        getString(R.string.select_directory_toast_message, uri)
        toast(getString(R.string.select_directory_toast_message, uri.lastPathSegment))
    }

    private fun handleCreateVideo(uri: Uri?) {
        if (uri == null) {
            toast(R.string.create_video_error_toast_message)
            return
        }

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                requireContext().contentResolver.openOutputStream(uri)?.bufferedWriter()
                    ?.use {
                        it.write("asdasd")
                    }
            }
        }
    }

    private fun createVideo() {
        createVideoLauncher.launch("new video.mp4")
    }

    companion object {
        private val PERMISSIONS = listOfNotNull(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
                .takeIf { !haveQ() }
        )
    }
}