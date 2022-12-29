package ru.skillbox.a31_35_backgroundwork

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay
import timber.log.Timber

class PeriodicWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        Timber.d("Start lengthy work")
        for (i in 0..100) {
            Timber.d("i=$i")
            delay(1000)
        }
        Timber.d("Finished lengthy work")
        return Result.success()
    }
}