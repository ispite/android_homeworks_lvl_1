package ru.skillbox.a31_35_backgroundwork.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.work.*
import ru.skillbox.a31_35_backgroundwork.DownloadWorker
import ru.skillbox.a31_35_backgroundwork.data.DownloadWorkerRepository
import timber.log.Timber

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = DownloadWorkerRepository()

    private val _workInfo = MutableLiveData<WorkInfo>()
    val workInfo: LiveData<WorkInfo>
        get() = _workInfo

    // https://stackoverflow.com/a/57090590
    private val observer = object : Observer<WorkInfo> {
        override fun onChanged(t: WorkInfo?) {
            Timber.d("observer $t")
        }
    }

    fun startDownload(urlToDownload: String) {
        val context = getApplication<Application>().applicationContext

        // ограничение по размеру 10Кбайт
        val workData = workDataOf(DownloadWorker.DOWNLOAD_URL_KEY to urlToDownload)

        val workConstraints = Constraints.Builder()
//            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiredNetworkType(NetworkType.NOT_ROAMING)
            .setRequiresBatteryNotLow(true)
            .build()

        val workRequest = repository.workRequest(workData, workConstraints)

        WorkManager.getInstance(context)
            .enqueueUniqueWork(DOWNLOAD_WORKER_ID, ExistingWorkPolicy.KEEP, workRequest)
    }

    fun observeWork() {
        val context = getApplication<Application>().applicationContext

        WorkManager.getInstance(context)
            .getWorkInfosForUniqueWorkLiveData(DOWNLOAD_WORKER_ID)
            .observeForever { it ->
                _workInfo.postValue(it.first())
                observer.onChanged(it.first())
            }
    }

    fun observePeriodicWork() {
        val context = getApplication<Application>().applicationContext

        WorkManager.getInstance(context)
            .getWorkInfosForUniqueWorkLiveData(DOWNLOAD_WORKER_ID)
            .observeForever { it ->
                _workInfo.postValue(it.first())
                observer.onChanged(it.first())
            }
    }


    override fun onCleared() {
        _workInfo.removeObserver(observer) // ???
//        observer.
        super.onCleared()
    }

    //TODO доделать 7й пункт: отмена задачи
    fun cancelWork() {
        val context = getApplication<Application>().applicationContext
        Timber.d("cancelWork")
        WorkManager.getInstance(context).cancelUniqueWork(DOWNLOAD_WORKER_ID)
    }

    //TODO доделать 8й пункт: отмена задачи
    fun periodicWork() {

    }

    companion object {
        private const val DOWNLOAD_WORKER_ID = "download worker"
        private const val PERIODIC_WORKER_ID = "periodic worker"
    }
}