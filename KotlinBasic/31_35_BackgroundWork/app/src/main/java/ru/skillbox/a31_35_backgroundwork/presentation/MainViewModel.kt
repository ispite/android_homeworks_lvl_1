package ru.skillbox.a31_35_backgroundwork.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import ru.skillbox.a31_35_backgroundwork.DownloadWorker
import ru.skillbox.a31_35_backgroundwork.data.DownloadWorkerRepository
import timber.log.Timber

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = DownloadWorkerRepository()
//    private val context = application.applicationContext

/*    init {
        val contextInInit = application.applicationContext
    }*/

    private val workInfo = MutableLiveData<WorkInfo>()


    // https://stackoverflow.com/a/57090590
    val observer = object : Observer<WorkInfo> {
        override fun onChanged(t: WorkInfo?) {
            Timber.d("observer $t")
        }
    }

    fun startDownload(urlToDownload: String) {
//        val context = getApplication<Application>().applicationContext

        // ограничение по размеру 10Кбайт
        val workData = workDataOf(DownloadWorker.DOWNLOAD_URL_KEY to urlToDownload)

        repository.startDownload(getApplication<Application>().applicationContext, workData)

        val workRequest = repository.workRequest(workData)


        WorkManager.getInstance(getApplication<Application>().applicationContext)
            .getWorkInfoByIdLiveData(workRequest.id)
            .observeForever { it ->
                workInfo.postValue(it)
                observer.onChanged(it)
            }
//            .observe(, {  })


    }


    override fun onCleared() {
        workInfo.removeObserver {  } // ???
//        observer.
        super.onCleared()
    }
}