package ru.skillbox.a31_35_backgroundwork.data

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import ru.skillbox.a31_35_backgroundwork.DownloadWorker
import java.util.concurrent.TimeUnit

class DownloadWorkerRepository {

/*    fun startDownload(context: Context, workData: Data) {
        val workRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setInputData(workData)
            .build()

        WorkManager.getInstance(context)
            .enqueue(workRequest)

*//*        WorkManager.getInstance(context)
            .getWorkInfoByIdLiveData(workRequest.id)
            .observe(viewLif)*//*
    }*/

    fun workRequest(workData: Data, workConstraints: Constraints) =
        OneTimeWorkRequestBuilder<DownloadWorker>()
            .setInputData(workData)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 20, TimeUnit.SECONDS)
            .setConstraints(workConstraints)
            .build()


}