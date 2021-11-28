package com.skillbox.multithreading.threading

import android.os.Message


/**
 * Created by Frank Tan on 3/04/2016.
 *
 * An interface for worker threads to send messages to the UI thread.
 * MainActivity implemented this Interface in this app.
 */
interface UiThreadCallback {
    fun publishToUiThread(message: Message?)
}
