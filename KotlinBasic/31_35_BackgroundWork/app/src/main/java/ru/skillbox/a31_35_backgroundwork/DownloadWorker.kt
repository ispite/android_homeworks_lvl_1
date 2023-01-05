package ru.skillbox.a31_35_backgroundwork

import android.content.Context
import android.os.Environment
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ru.skillbox.a31_35_backgroundwork.network.Networking
import timber.log.Timber
import java.io.File
import java.net.URL

class DownloadWorker(private val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val urlToDownload = inputData.getString(DOWNLOAD_URL_KEY) ?: ""
        Timber.d("work started")
        download(urlToDownload) { return if (it) Result.success() else Result.failure() }
//        return Result.success()
    }

    private suspend fun download(
        url: String,
        onResult: (Boolean) -> Unit
        /*onSuccess: () -> Unit, onFailure: () -> Unit*/
    ) {
        if (url.isEmpty()) return
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            val folder = context.getExternalFilesDir("Downloads")
            val file = File(folder, getFileName(url))
            try {
                Timber.d("download started")
                file.outputStream().use { fileOutputStream ->
                    Networking.api.getFile(url)
                        .byteStream()
                        .use { inputStream ->
                            inputStream.copyTo(fileOutputStream)
                        }
                }
                onResult(true)
            } catch (t: Throwable) {
                file.delete()
                Timber.e("file deleted", t)
                onResult(false)
            }
        }
    }

    private fun getFileName(fileUrl: String): String {
        var fileName = URL(fileUrl).file
        fileName = fileName.substring(fileName.lastIndexOf('/') + 1)
        return fileName
    }

    companion object {
        const val DOWNLOAD_URL_KEY = "download_url"
    }
}

