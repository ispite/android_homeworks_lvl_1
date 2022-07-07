package ru.skillbox.a25_29_contentprovider.my_content_provider

import android.content.*
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.util.Log
import com.squareup.moshi.Moshi
import ru.skillbox.a25_29_contentprovider.BuildConfig
import ru.skillbox.a25_29_contentprovider.data.Course

class MyContentProvider : ContentProvider() {

    private lateinit var coursePrefs: SharedPreferences

    private val courseAdapter = Moshi.Builder().build().adapter(Course::class.java)

    init {
        Log.d("MyContentProvider", "Thread init: ${Thread.currentThread().name}")
    }

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(AUTHORITIES, PATH_COURSES, TYPE_COURSES)
        addURI(AUTHORITIES, "$PATH_COURSES/#", TYPE_COURSE_ID)
        Log.d("MyContentProvider", "Thread uriMatcher: ${Thread.currentThread().name}")

    }

    override fun onCreate(): Boolean {
        coursePrefs = context!!.getSharedPreferences("course_shared_prefs", Context.MODE_PRIVATE)
        Log.d("MyContentProvider", "Thread onCreate: ${Thread.currentThread().name}")
        return true
    }

    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        return when (uriMatcher.match(p0)) {
            TYPE_COURSES -> {
                Log.d("MyContentProvider", "Thread TYPE_COURSES: ${Thread.currentThread().name}")
                getAllCoursesCursor()
            }
            TYPE_COURSE_ID -> {
                Log.d("MyContentProvider", "Thread TYPE_COURSE_ID: ${Thread.currentThread().name}")
                val id = p0.lastPathSegment
                getCourseByIDCursor(id!!.toLong())
            }
            else -> null
        }
    }

    private fun getCourseByIDCursor(id: Long): Cursor {
        val allCourses = coursePrefs.all.mapNotNull {
            val courseJsonString = it.value as String
            courseAdapter.fromJson(courseJsonString)
        }

        val cursor =
            MatrixCursor(arrayOf(COLUMN_COURSE_ID, COLUMN_COURSE_TITLE, COLUMN_COURSE_DURATION))
        allCourses.mapNotNull { course ->
            course.takeIf { it.id == id }?.let {
                cursor.newRow()
                    .add(it.id)
                    .add(it.title)
                    .add(it.duration)
            }
        }
        Log.d("MyContentProvider", "Thread getCourseByIDCursor: ${Thread.currentThread().name}")
        return cursor
    }

    private fun getAllCoursesCursor(): Cursor {
        val allCourses = coursePrefs.all.mapNotNull {
            val courseJsonString = it.value as String
            courseAdapter.fromJson(courseJsonString)
        }

        val cursor =
            MatrixCursor(arrayOf(COLUMN_COURSE_ID, COLUMN_COURSE_TITLE, COLUMN_COURSE_DURATION))
        allCourses.forEach {
            cursor.newRow()
                .add(it.id)
                .add(it.title)
                .add(it.duration)
        }
        Log.d("MyContentProvider", "Thread getAllCoursesCursor: ${Thread.currentThread().name}")
        return cursor
    }

    override fun getType(p0: Uri): String? {
        return null
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        p1 ?: return null
        return when (uriMatcher.match(p0)) {
            TYPE_COURSES -> {
                Log.d("MyContentProvider", "Thread insert: ${Thread.currentThread().name}")
                saveCourse(p1)
            }
            else -> null
        }
    }

    private fun saveCourse(contentValues: ContentValues): Uri? {
        val id = contentValues.getAsLong(COLUMN_COURSE_ID) ?: return null
        val title = contentValues.getAsString(COLUMN_COURSE_TITLE) ?: return null
        val duration = contentValues.getAsLong(COLUMN_COURSE_DURATION) ?: return null
        val course = Course(id, title, duration)
        coursePrefs.edit()
            .putString(id.toString(), courseAdapter.toJson(course))
            .commit()
        Log.d("MyContentProvider", "Thread saveCourse: ${Thread.currentThread().name}")
        return Uri.parse("content://$AUTHORITIES/$PATH_COURSES/$id")
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        return when (uriMatcher.match(p0)) {
            TYPE_COURSE_ID -> {
                Log.d("MyContentProvider", "Thread delete TYPE_COURSE_ID: ${Thread.currentThread().name}")
                deleteCourse(p0)
            }
            TYPE_COURSES -> {
                Log.d("MyContentProvider", "Thread delete TYPE_COURSES: ${Thread.currentThread().name}")
                deleteAllCourses()
            }
            else -> 0
        }
    }

    private fun deleteCourse(uri: Uri): Int {
        val courseId = uri.lastPathSegment?.toLongOrNull()?.toString() ?: return 0
        return if (coursePrefs.contains(courseId)) {
            coursePrefs.edit()
                .remove(courseId)
                .commit()
            Log.d("MyContentProvider", "Thread deleteCourse: ${Thread.currentThread().name}")
            1
        } else {
            0
        }
    }

    private fun deleteAllCourses(): Int {
        return if (coursePrefs.all.isNotEmpty()) {
            var count = 0
            for ((key, value) in coursePrefs.all) count++
            coursePrefs.edit().clear().commit()
            Log.d("MyContentProvider", "Thread deleteAllCourses: ${Thread.currentThread().name}")
            count
        } else {
            0
        }
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        p1 ?: return 0
        return when (uriMatcher.match(p0)) {
            TYPE_COURSE_ID -> {
                Log.d("MyContentProvider", "Thread update: ${Thread.currentThread().name}")
                updateCourse(p0, p1)
            }
            else -> 0
        }
    }

    private fun updateCourse(uri: Uri, contentValues: ContentValues): Int {
        val courseId = uri.lastPathSegment?.toLongOrNull()?.toString() ?: return 0
        return if (coursePrefs.contains(courseId)) {
            saveCourse(contentValues)
            Log.d("MyContentProvider", "Thread updateCourse: ${Thread.currentThread().name}")
            1
        } else {
            0
        }
    }

    companion object {
        private const val AUTHORITIES = "${BuildConfig.APPLICATION_ID}.provider"
        private const val PATH_COURSES = "courses"

        private const val TYPE_COURSES = 1
        private const val TYPE_COURSE_ID = 2

        private const val COLUMN_COURSE_ID = "id"
        private const val COLUMN_COURSE_TITLE = "title"
        private const val COLUMN_COURSE_DURATION = "duration"
    }
}