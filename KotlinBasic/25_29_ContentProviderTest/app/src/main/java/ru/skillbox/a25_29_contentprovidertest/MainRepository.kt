package ru.skillbox.a25_29_contentprovidertest

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.skillbox.a25_29_contentprovidertest.data.Constants
import ru.skillbox.a25_29_contentprovidertest.data.Course

class MainRepository(private val context: Context) {

    suspend fun saveRandomCourse() =
        withContext(Dispatchers.IO) {
            val titleList = listOf("Programing course", "Design course", "Marketing course")
            val durationList = listOf(259200000L, 691200000, 417600000)
            var id: Long = 0
            try {
                id = getLastCourseID() + 1
            } catch (e: Exception) {
                Log.d("MainRepository", "saveBunchRandomCourses: ", e)
            }
            saveCourse(id, titleList.random(), durationList.random())
        }

    suspend fun saveBunchRandomCourses(count: Int) = withContext(Dispatchers.IO) {
        val titleList = listOf("Programing course", "Design course", "Marketing course")
        val durationList = listOf(259200000L, 691200000, 417600000)

        var id: Long = 0
        try {
            id = getLastCourseID() + 1
        } catch (e: Exception) {
            Log.d("MainRepository", "saveBunchRandomCourses: ", e)
        }

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
            null,
            null
        )?.use {
            getLastIDFromCursor(it)
        } ?: throw Exception("Content resolver error")
    }

    private fun getLastIDFromCursor(cursor: Cursor): Long {
        if (cursor.moveToFirst().not()) throw Exception("Cursor can not reach course ID")
        val sortedList = getCourseFromCursor(cursor).sortedByDescending { it.id }
//        Log.d("MainRepository", "getLastIDFromCursor: $sortedList")
//        Log.d("MainRepository", "getLastIDFromCursor: ${sortedList[0].id}")
        return sortedList[0].id
    }

    private fun saveCourse(id: Long, title: String, duration: Long): Long {
        val contentValues = ContentValues().apply {
            put("id", id)
            put("title", title)
            put("duration", duration)
        }

        return try {
            val uri = context.contentResolver.insert(
                Uri.parse("content://ru.skillbox.a25_29_contentprovider.provider/courses"),
                contentValues
            )
            uri?.lastPathSegment?.toLongOrNull() ?: error("cannot save raw course")
        } catch (e: Exception) {
            Log.d("MainRepository", "saveCourse: $e").toLong()
        }
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

    suspend fun getCourseByID(id: Long): List<Course> = withContext(Dispatchers.IO) {
        context.contentResolver.query(
            Uri.withAppendedPath(Uri.parse(Constants.URI_COURSES), id.toString()),
            null,
            null,
            null,
            null
        )?.use {
//            Log.d("MainRepository", "getCourseByID: ${it}")
            getCourseFromCursor(it)
        }.orEmpty()
    }

    fun deleteCourseById(id: Long): Int {
        return try {
            context.contentResolver.delete(
                Uri.withAppendedPath(Uri.parse(Constants.URI_COURSES), id.toString()),
                null,
                null
            )
        } catch (e: Exception) {
            Log.d("MainRepository", "deleteCourseById: $e")
        }

    }

    fun deleteAllCourses(): Int {
        return try {
            context.contentResolver.delete(
                Uri.parse("content://ru.skillbox.a25_29_contentprovider.provider/courses"),
                null,
                null
            )
        } catch (e: Exception) {
            Log.d("MainRepository", "deleteAllCourses: $e")
        }
    }

    fun updateCourseById(id: Long, title: String, duration: Long): Int {
        val contentValues = ContentValues().apply {
            put("id", id)
            put("title", title)
            put("duration", duration)
        }
        return try {
            context.contentResolver.update(
                Uri.withAppendedPath(Uri.parse(Constants.URI_COURSES), id.toString()),
                contentValues,
                null,
                null
            )
        } catch (e: Exception) {
            Log.d("MainRepository", "updateCourseById: $e")
        }
    }
}