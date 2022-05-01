package ru.skillbox.a25_29_contentprovidertest

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.skillbox.a25_29_contentprovidertest.data.Course

class MainRepository(private val context: Context) {

/*    suspend fun saveRandomCourse(*//*id: Long, title: String, duration: Long*//*) =
        withContext(Dispatchers.IO) {
            val courseID = saveCourseID()

        }*/

    suspend fun saveBunchRandomCourses(count: Int) = withContext(Dispatchers.IO) {
        val titleList = listOf("Programing course", "Design course", "Marketing course")
        val durationList = listOf(259200000L, 691200000, 417600000)
        val id = getLastCourseID()

        (0 until count).map {
            saveCourse(it + id, titleList.random(), durationList.random())
        }
    }

    private suspend fun getLastCourseID(): Long = withContext(Dispatchers.IO) {
        context.contentResolver.query(
            Uri.parse("content://ru.skillbox.a25_29_contentprovider.provider/courses"),
            null,
            null,
            null,
            "id DESC"
        )?.use {
            getLastIDFromCursor(it)
        } ?: throw Exception("Content resolver error")
    }

    private fun getLastIDFromCursor(cursor: Cursor): Long {
        if (cursor.moveToFirst().not()) throw Exception("Cursor can not reach course ID")
        val idIndex = cursor.getColumnIndex("id")
        return cursor.getLong(idIndex)
    }

/*    private fun saveCourseID(): Long {
        val contentValues = ContentValues().apply {
            put("id", 1L)
            put("title", "Awesome course")
            put("duration", 259200000L)
        }

        val uri = context.contentResolver.insert(
            //Uri.parse("content://ru.skillbox.a25_29_contentprovider.provider"),
            Uri.parse("content://ru.skillbox.a25_29_contentprovider.provider/courses"),
            //Uri.parse("content://ru.skillbox.a25_29_contentprovider.provider/courses/1"),
            contentValues
        )

        Log.d("MainRepository", "saveCourseID: $uri")
        return uri?.lastPathSegment?.toLongOrNull() ?: error("cannot save raw course")
    }*/

    private fun saveCourse(id: Long, title: String, duration: Long): Long {
        val contentValues = ContentValues().apply {
            put("id", id)
            put("title", title)
            put("duration", duration)
        }

        val uri = context.contentResolver.insert(
            Uri.parse("content://ru.skillbox.a25_29_contentprovider.provider/courses"),
            contentValues
        )

        Log.d("MainRepository", "saveCourseID: $uri")
        return uri?.lastPathSegment?.toLongOrNull() ?: error("cannot save raw course")
    }

    suspend fun getAllCourses(): List<Course> = withContext(Dispatchers.IO) {
        context.contentResolver.query(
            Uri.parse("content://ru.skillbox.a25_29_contentprovider.provider/courses"),
            null,
            null,
            null,
            "id ASC"
        )?.use {
            getCourseFromCursor(it)
        }.orEmpty()
    }

    private fun getCourseFromCursor(cursor: Cursor): List<Course> {
        if (cursor.moveToFirst().not()) return emptyList()
        val list = mutableListOf<Course>()
        do {
            val titleIndex = cursor.getColumnIndex("title")
            val title = cursor.getString(titleIndex).orEmpty()
            val idIndex = cursor.getColumnIndex("id")
            val id = cursor.getLong(idIndex)
            val durationIndex = cursor.getColumnIndex("duration")
            val duration = cursor.getLong(durationIndex)

            list.add(Course(id = id, title = title, duration = duration))
        } while (cursor.moveToNext())
        return list
    }
}