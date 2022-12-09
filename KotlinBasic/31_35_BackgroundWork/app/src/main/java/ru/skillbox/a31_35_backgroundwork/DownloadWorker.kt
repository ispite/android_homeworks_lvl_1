package ru.skillbox.a31_35_backgroundwork

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import timber.log.Timber

class DownloadWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val urlToDownload = inputData.getString(DOWNLOAD_URL_KEY)
        Timber.d("doWork $urlToDownload")
        return Result.success()
    }

    companion object {
        const val DOWNLOAD_URL_KEY = "download_url"
    }
}