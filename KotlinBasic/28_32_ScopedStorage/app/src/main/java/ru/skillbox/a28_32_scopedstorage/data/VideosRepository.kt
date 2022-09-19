package ru.skillbox.a28_32_scopedstorage.data

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.skillbox.a28_32_scopedstorage.network.Networking
import ru.skillbox.a28_32_scopedstorage.utils.haveQ
import timber.log.Timber

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

    suspend fun saveRemoteVideo(title: String, url: String) {
        withContext(Dispatchers.IO) {
            val videoUri = saveVideoDetails(title)
            downloadVideo(url, videoUri)
            makeVideoReady(videoUri)
        }
    }

    private fun saveVideoDetails(title: String): Uri {
        val volume = if (haveQ()) {
            MediaStore.VOLUME_EXTERNAL_PRIMARY
        } else {
            MediaStore.VOLUME_EXTERNAL
            // Чтобы убрать предупреждение по идее надо восполдьзоваться этим рещением
            // https://stackoverflow.com/a/63756762
//            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        }

        val videoCollectionUri = MediaStore.Video.Media.getContentUri(volume)
        val videoDetails = ContentValues().apply {
            put(MediaStore.Video.Media.DISPLAY_NAME, title)
            put(MediaStore.Video.Media.MIME_TYPE, "video/*")
            if (haveQ()) {
                put(MediaStore.Video.Media.IS_PENDING, 1)
                put(MediaStore.Video.Media.RELATIVE_PATH, "Movies")
            }
        }

        return context.contentResolver.insert(videoCollectionUri, videoDetails)!!
    }

    private suspend fun downloadVideo(url: String, uri: Uri) {
        try {
            context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                Networking.api.getFile(url).byteStream().use { inputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        } catch (t: Throwable) {
            context.contentResolver.delete(uri, null, null)
            Timber.e(t)
        }
    }

    private fun makeVideoReady(videoUri: Uri) {
        if (haveQ().not()) return
        val videoDetails = ContentValues().apply {
            put(MediaStore.Video.Media.IS_PENDING, 0)
        }
        context.contentResolver.update(videoUri, videoDetails, null, null)
    }
}