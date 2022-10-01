package ru.skillbox.a28_32_scopedstorage.data

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.IntentSender
import android.database.ContentObserver
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.skillbox.a28_32_scopedstorage.network.Networking
import ru.skillbox.a28_32_scopedstorage.utils.haveQ
import ru.skillbox.a28_32_scopedstorage.utils.haveR
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

                    if (haveR()) {
                        val favorite =
                            cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.IS_FAVORITE))
                        videos += Video(id, uri, name, size, favorite)
                    } else {
                        videos += Video(id, uri, name, size, null)
                    }
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

    suspend fun downloadVideoFromUrl(
        uri: Uri,
        url: String,
        success: (Boolean) -> Unit
    ) {
        try {
            context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                Networking.api.getFile(url).byteStream().use { inputStream ->
                    Timber.d("Started copying")
                    inputStream.copyTo(outputStream)
                    success(true)
                }
            }
        } catch (t: Throwable) {
            Timber.e(t)
            success(false)
        }
    }

    private fun makeVideoReady(videoUri: Uri) {
        if (haveQ().not()) return
        val videoDetails = ContentValues().apply {
            put(MediaStore.Video.Media.IS_PENDING, 0)
        }
        context.contentResolver.update(videoUri, videoDetails, null, null)
    }

    suspend fun deleteVideo(id: Long) {
        withContext(Dispatchers.IO) {
            val uri =
                ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)
            Timber.d("MIME type is: ${getMimeType(uri)}")
            context.contentResolver.delete(uri, null, null)
        }
    }

    private fun getMimeType(uri: Uri) =
        MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(context.contentResolver.getType(uri))


    @RequiresApi(Build.VERSION_CODES.R) // R версия = 11 android, API 30
    fun addToTrash(id: Long, state: Boolean): IntentSender {
        val uri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)
        return MediaStore.createTrashRequest(
            context.contentResolver,
            listOf(uri),
            state
        ).intentSender
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun addToFavorite(id: Long, state: Boolean): IntentSender {
        val uri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)

        return MediaStore.createFavoriteRequest(
            context.contentResolver,
            listOf(uri),
            state.not()
        ).intentSender
    }
}