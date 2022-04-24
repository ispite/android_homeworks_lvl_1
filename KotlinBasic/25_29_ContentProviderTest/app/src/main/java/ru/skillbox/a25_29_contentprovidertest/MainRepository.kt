package ru.skillbox.a25_29_contentprovidertest

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URI
import java.net.URL

class MainRepository(private val context: Context) {

    suspend fun saveRandomCourse(/*id: Long, title: String, duration: Long*/) = withContext(Dispatchers.IO) {
        val courseID = saveCourseID()

    }

    private fun saveCourseID(): Long {
        val uri = context.contentResolver.insert(
            //Uri.parse("ru.skillbox.a25_29_contentprovider.my_content_provider.MyContentProvider"),
            //Uri.parse("content://ru.skillbox.a25_29_contentprovider.my_content_provider.MyContentProvider"),
            //Uri.parse("ru.skillbox.a25_29_contentprovider.provider/courses"),
            //Uri.parse("content://ru.skillbox.a25_29_contentprovider.provider/courses"),
            //Uri.parse("ru.skillbox.a25_29_contentprovider.provider"),
            Uri.parse("content://ru.skillbox.a25_29_contentprovider.provider"),
            ContentValues()
        )

        Log.d("MainRepository", "saveCourseID: $uri")
        return uri?.lastPathSegment?.toLongOrNull() ?: error("cannot save raw course")
    }
}