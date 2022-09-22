package ru.skillbox.a28_32_scopedstorage.presentation.main

import android.app.Application
import android.app.RecoverableSecurityException
import android.app.RemoteAction
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.skillbox.a28_32_scopedstorage.R
import ru.skillbox.a28_32_scopedstorage.data.Video
import ru.skillbox.a28_32_scopedstorage.data.VideosRepository
import ru.skillbox.a28_32_scopedstorage.utils.SingleLiveEvent
import ru.skillbox.a28_32_scopedstorage.utils.haveQ
import timber.log.Timber

class VideoListViewModel(app: Application) : AndroidViewModel(app) {

    private val videosRepository = VideosRepository(app)

    private val _videoList = MutableLiveData<List<Video>>()
    private val _toast = SingleLiveEvent<Int>()
    private val _permissionGranted = MutableLiveData(true)
    private val _recoverableAction = MutableLiveData<RemoteAction>()

    private var isObservingState: Boolean = false
    private var pendingDelete: Long? = null

    val videoList: LiveData<List<Video>>
        get() = _videoList

    val toast: LiveData<Int>
        get() = _toast

    val permissionGranted: LiveData<Boolean>
        get() = _permissionGranted

    val recoverableAction: LiveData<RemoteAction>
        get() = _recoverableAction

    fun updatePermissionState(isGranted: Boolean) {
        if (isGranted) {
            permissionGranted()
        } else {
            permissionDenied()
        }
    }

    fun permissionGranted() {
        Timber.d("start permissionGranted")
        loadVideos()
        if (isObservingState.not()) {
            videosRepository.observeVideos { loadVideos() }
            isObservingState = true
        }
        _permissionGranted.postValue(true)
    }

    fun permissionDenied() {
        _permissionGranted.postValue(false)
    }

    private fun loadVideos() {
        viewModelScope.launch {
            Timber.d("start loadVideos")
            try {
                val videos = videosRepository.getVideos()
                Timber.d("videos $videos")
                _videoList.postValue(videos)
            } catch (t: Throwable) {
                Timber.e("loadVideos error: $t")
                _videoList.postValue(emptyList())
                _toast.postValue(R.string.video_list_error)
            }
        }
    }

    fun deleteVideo(id: Long) {
        viewModelScope.launch {
            try {
                videosRepository.deleteImage(id)
                pendingDelete = null
            } catch (t: Throwable) {
                Timber.e(t)
                if (haveQ() && t is RecoverableSecurityException) {
                    pendingDelete = id
                    _recoverableAction.postValue(t.userAction)
                } else {
                    _toast.postValue(R.string.video_delete_error)
                }
            }
        }
    }

    fun confirmDelete() {
        pendingDelete?.let {
            deleteVideo(it)
        }
    }

    fun declineDelete() {
        pendingDelete = null
    }
}