package ru.skillbox.a31_35_backgroundwork.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.work.*
import ru.skillbox.a31_35_backgroundwork.DownloadWorker
import ru.skillbox.a31_35_backgroundwork.PeriodicWorker
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val workManager = WorkManager.getInstance(application.applicationContext)

    val workInfo: LiveData<List<WorkInfo>> =
        workManager.getWorkInfosForUniqueWorkLiveData(DOWNLOAD_WORKER_ID)

    private val _workPeriodicInfo = MutableLiveData<WorkInfo>()
    val workPeriodicInfo: LiveData<WorkInfo>
        get() = _workPeriodicInfo

    // https://stackoverflow.com/a/57090590
    private val observer = object : Observer<List<WorkInfo>> {
        override fun onChanged(t: List<WorkInfo>?) {
            Timber.d("observer $t")
        }
    }

    private val periodicObserver = Observer<WorkInfo> { t -> Timber.d("periodic observer $t") }

    fun startDownload(urlToDownload: String) {
        val context = getApplication<Application>().applicationContext

        // ограничение по размеру 10Кбайт
        val workData = workDataOf(DownloadWorker.DOWNLOAD_URL_KEY to urlToDownload)

        val workConstraints = Constraints.Builder()
//            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiredNetworkType(NetworkType.NOT_ROAMING)
            .setRequiresBatteryNotLow(true)
            .build()

//        val workRequest = repository.workRequest(workData, workConstraints)
        val workRequest =
            OneTimeWorkRequestBuilder<DownloadWorker>()
                .setInputData(workData)
                // ДЗ пункт 4
                .setBackoffCriteria(BackoffPolicy.LINEAR, 20, TimeUnit.SECONDS)
                .setConstraints(workConstraints)
                .build()

        WorkManager.getInstance(context)
            .enqueueUniqueWork(DOWNLOAD_WORKER_ID, ExistingWorkPolicy.KEEP, workRequest)
    }

    fun observeWork() {

        workInfo.observeForever(observer)

    }

    fun observePeriodicWork() {
        val context = getApplication<Application>().applicationContext

        WorkManager.getInstance(context)
            .getWorkInfosForUniqueWorkLiveData(PERIODIC_WORKER_ID)
            .observeForever { it ->
                Timber.d("observeForever Periodic List=$it")
                if (it.isNotEmpty()) {
                    _workPeriodicInfo.postValue(it.first())
                    periodicObserver.onChanged(it.first())
                }
            }
    }

    override fun onCleared() {
        workInfo.removeObserver(observer)
        super.onCleared()
    }

    fun cancelWork() {
        val context = getApplication<Application>().applicationContext
        Timber.d("cancelWork")
        WorkManager.getInstance(context).cancelUniqueWork(DOWNLOAD_WORKER_ID)
    }

    fun periodicWork() {
        val context = getApplication<Application>().applicationContext
//        val periodicWorkRequest = repository.periodicWorkRequest()
        val periodicWorkRequest =
            PeriodicWorkRequestBuilder<PeriodicWorker>(15, TimeUnit.MINUTES)
                .build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                PERIODIC_WORKER_ID,
                ExistingPeriodicWorkPolicy.REPLACE,
                periodicWorkRequest
            )
    }

    companion object {
        private const val DOWNLOAD_WORKER_ID = "download worker"
        private const val PERIODIC_WORKER_ID = "periodic worker"
    }
}