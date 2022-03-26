package ru.skillbox.a24_28_files.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Environment
import android.util.Log
import kotlinx.coroutines.coroutineScope
import ru.skillbox.a24_28_files.network.Networking
import java.io.File
import java.net.URL
import kotlin.coroutines.suspendCoroutine

class MainRepository(context: Context) {

    private val contextRepository = context
    private val sharedPrefs by lazy {
        contextRepository.getSharedPreferences(SHARED_PREFS_FILENAME, Context.MODE_PRIVATE)
    }

    suspend fun downloadFile(
        fileUrl: String,
        fileNameCallback: (String) -> Unit,
        success: (Boolean) -> Unit
    ) {
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            val folder = contextRepository.getExternalFilesDir("myFolder")
            val timestamp = System.currentTimeMillis() / 1000
            var fileName = URL(fileUrl).file
            fileName = fileName.substring(fileName.lastIndexOf('/') + 1)
            fileName = timestamp.toString() + "_" + fileName
            fileNameCallback(fileName)
            val file = File(folder, fileName)
            try {
                file.outputStream().use { fileOutputStream ->
                    Networking.api
                        .getFile(fileUrl)
                        .byteStream()
                        .use { inputStream ->
                            inputStream.copyTo(fileOutputStream)
                            success(true)
                        }
                }
            } catch (t: Throwable) {
                success(false)
                file.delete()
                Log.d("MainRepository", "delete File: ${t.message}", t)
            }
        }
    }

    suspend fun createFileExternalStorage() {
        coroutineScope {
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                Log.d("MainRepository", "createFileExternalStorage: ${Environment.MEDIA_MOUNTED}")
                val testFolder = contextRepository.getExternalFilesDir("testFolder")
                val testFile = File(testFolder, "external_test_file.txt")
                try {
                    testFile.outputStream().buffered().use {
                        it.write("Content in external storage file".toByteArray())
                    }
                } catch (t: Throwable) {
                    Log.d("MainRepository", "createFileExternalStorage: ${t.message}", t)
                }
            }
        }
    }

    suspend fun saveSharedPrefsInfo(fileUrl: String, fileName: String) {
        Log.d("MainRepository", "saveSharedPrefsInfo: $fileUrl, $fileName")
        sharedPrefs.edit()
            .putString(fileUrl, fileName)
            .apply()
    }

    fun checkFileExistence(fileUrl: String, fileName: (String) -> Unit): Boolean {
        fileName(sharedPrefs.getString(fileUrl, null).orEmpty())
        return sharedPrefs.contains(fileUrl)
    }

    suspend fun downloadOnFirstRun() {
        val file = contextRepository.resources.assets.open("list_of_links_on_first_run.txt")
            .bufferedReader()
            .readLines()
        if (!sharedPrefs.getBoolean(SHARED_PREFS_FIRST_RUN_KEY, false)) {
            file.map {
                downloadFile(it, {}, {})
            }
            sharedPrefs.edit()
                .putBoolean(SHARED_PREFS_FIRST_RUN_KEY, true)
                .apply()
        }
    }

    companion object {
        private const val SHARED_PREFS_FILENAME = "shared_prefs_data"
        private const val SHARED_PREFS_FIRST_RUN_KEY = "first_run"
    }
}