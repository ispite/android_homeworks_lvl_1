package ru.skillbox.a28_32_scopedstorage.presentation.main.download

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.skillbox.a28_32_scopedstorage.R
import ru.skillbox.a28_32_scopedstorage.data.VideosRepository
import ru.skillbox.a28_32_scopedstorage.utils.SingleLiveEvent
import timber.log.Timber

class DownloadVideoViewModel(app: Application) : AndroidViewModel(app) {

    private val videosRepository = VideosRepository(app)

    private val _loading = MutableLiveData(false)
    private val _saveSuccess = SingleLiveEvent<Unit>()
    private val _toast = SingleLiveEvent<Int>()

    val loading: LiveData<Boolean>
        get() = _loading

    val saveSuccess: LiveData<Unit>
        get() = _saveSuccess

    val toast: LiveData<Int>
        get() = _toast

    fun downloadVideo(title: String, url: String) {
        viewModelScope.launch {
            _loading.postValue(true)
            try {
                videosRepository.saveRemoteVideo(title, url)
                _saveSuccess.postValue(Unit)
            } catch (t: Throwable) {
                Timber.e(t)
                _toast.postValue(R.string.video_download_error)
            } finally {
                _loading.postValue(false)
            }
        }
    }
}