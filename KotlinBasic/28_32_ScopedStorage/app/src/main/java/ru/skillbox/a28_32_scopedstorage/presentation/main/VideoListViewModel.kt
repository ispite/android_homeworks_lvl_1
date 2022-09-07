package ru.skillbox.a28_32_scopedstorage.presentation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.skillbox.a28_32_scopedstorage.R
import ru.skillbox.a28_32_scopedstorage.data.Video
import ru.skillbox.a28_32_scopedstorage.data.VideosRepository
import ru.skillbox.a28_32_scopedstorage.utils.SingleLiveEvent
import timber.log.Timber

class VideoListViewModel(app: Application) : AndroidViewModel(app) {

    private val videosRepository = VideosRepository(app)

    private val _videoList = MutableLiveData<List<Video>>()
    private val _toast = SingleLiveEvent<Int>()

    val videoList: LiveData<List<Video>>
        get() = _videoList

    val toast: LiveData<Int>
        get() = _toast

    fun permissionGranted() {
        loadVideos()
    }

    private fun loadVideos() {
        viewModelScope.launch {
            try {
                val videos = videosRepository.getVideos()
                _videoList.postValue(videos)
            } catch (t: Throwable) {
                Timber.e(t)
                _videoList.postValue(emptyList())
                _toast.postValue(R.string.video_list_error)
            }
        }
    }
}