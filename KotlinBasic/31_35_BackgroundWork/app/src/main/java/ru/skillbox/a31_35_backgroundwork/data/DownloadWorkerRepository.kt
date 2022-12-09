package ru.skillbox.a31_35_backgroundwork.data

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import ru.skillbox.a31_35_backgroundwork.DownloadWorker

class DownloadWorkerRepository {

    fun startDownload(context: Context, workData: Data) {
        val workRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setInputData(workData)
            .build()

        WorkManager.getInstance(context)
            .enqueue(workRequest)

/*        WorkManager.getInstance(context)
            .getWorkInfoByIdLiveData(workRequest.id)
            .observe(viewLif)*/
    }

    fun workRequest(workData: Data) = OneTimeWorkRequestBuilder<DownloadWorker>()
        .setInputData(workData)
        .build()


}