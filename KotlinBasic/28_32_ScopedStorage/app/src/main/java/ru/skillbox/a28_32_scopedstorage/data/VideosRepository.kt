package ru.skillbox.a28_32_scopedstorage.data

import android.content.ContentUris
import android.content.Context
import android.database.ContentObserver
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideosRepository(private val context: Context) {

    private var observer: ContentObserver? = null

    fun observeVideos(onChange: () -> Unit) {
        observer = object : ContentObserver(Handler(Looper.getMainLooper())) {
            override fun onChange(selfChange: Boolean) {
                super.onChange(selfChange)
                onChange()
            }
        }
        context.contentResolver.registerContentObserver(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            true,
            observer!!
        )
    }

    suspend fun getVideos(): List<Video> {
        val videos = mutableListOf<Video>()
        withContext(Dispatchers.IO) {
            context.contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                null
            )?.use { cursor ->
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID))
                    val name =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME))
                    val size =
                        cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE))
                    val uri =
                        ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)

                    videos += Video(id, uri, name, size)
                }
            }
        }
        return videos
    }

}