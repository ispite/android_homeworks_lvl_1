package ru.skillbox.a31_35_backgroundwork.data

import androidx.work.*
import ru.skillbox.a31_35_backgroundwork.DownloadWorker
import ru.skillbox.a31_35_backgroundwork.PeriodicWorker
import java.util.concurrent.TimeUnit

class DownloadWorkerRepository {

    fun workRequest(workData: Data, workConstraints: Constraints) =
        OneTimeWorkRequestBuilder<DownloadWorker>()
            .setInputData(workData)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 20, TimeUnit.SECONDS)
            .setConstraints(workConstraints)
            .build()

    fun periodicWorkRequest() =
        PeriodicWorkRequestBuilder<PeriodicWorker>(15, TimeUnit.MINUTES)
            .build()

}