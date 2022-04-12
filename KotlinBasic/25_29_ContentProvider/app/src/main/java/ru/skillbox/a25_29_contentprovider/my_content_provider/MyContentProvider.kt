package ru.skillbox.a25_29_contentprovider.my_content_provider

import android.content.*
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import com.squareup.moshi.Moshi
import ru.skillbox.a25_29_contentprovider.BuildConfig
import ru.skillbox.a25_29_contentprovider.data.Course

class MyContentProvider : ContentProvider() {

    private lateinit var coursePrefs: SharedPreferences

    private val courseAdapter = Moshi.Builder().build().adapter(Course::class.java)

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(AUTHORITIES, PATH_COURSES, TYPE_COURSES)
        addURI(AUTHORITIES, "$PATH_COURSES/#", TYPE_COURSE_ID)
    }

    override fun onCreate(): Boolean {
        coursePrefs = context!!.getSharedPreferences("course_shared_prefs", Context.MODE_PRIVATE)
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
            TYPE_COURSES -> getAllCoursesCursor()
            else -> null
        }
    }

    private fun getAllCoursesCursor(): Cursor {
        val allCourses = coursePrefs.all.mapNotNull {
            val courseJsonString = it.value as String
            courseAdapter.fromJson(courseJsonString)
        }

        val cursor = MatrixCursor(arrayOf(COLUMN_COURSE_ID, COLUMN_COURSE_TITLE, COLUMN_COURSE_DURATION))
        allCourses.forEach {
            cursor.newRow()
                .add(it.id)
                .add(it.title)
                .add(it.duration)
        }
        return cursor
    }

    override fun getType(p0: Uri): String? {
        return null
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        p1 ?: return null
        return when (uriMatcher.match(p0)) {
            //TYPE_COURSES ->
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
        return Uri.parse("content://$AUTHORITIES/$PATH_COURSES/$id")
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        return when(uriMatcher.match(p0)) {
            TYPE_COURSE_ID ->deleteCourse(p0)
            else -> 0
        }
    }

    private fun deleteCourse(uri: Uri): Int {
        val courseId = uri.lastPathSegment?.toLongOrNull()?.toString() ?: return 0
        return if (coursePrefs.contains(courseId)) {
            coursePrefs.edit()
                .remove(courseId)
                .commit()
            1
        } else {
            0
        }
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        p1 ?: return 0
        return when(uriMatcher.match(p0)){
            TYPE_COURSE_ID -> updateCourse(p0, p1)
            else -> 0
        }
    }

    private fun updateCourse(uri: Uri, contentValues: ContentValues): Int {
        val courseId = uri.lastPathSegment?.toLongOrNull()?.toString() ?: return 0
        return if(coursePrefs.contains(courseId)) {
            saveCourse(contentValues)
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