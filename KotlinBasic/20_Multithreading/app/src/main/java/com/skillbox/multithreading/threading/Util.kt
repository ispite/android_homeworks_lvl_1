package com.skillbox.multithreading.threading

import android.os.Bundle
import android.os.Message
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Frank Tan on 10/04/2016.
 *
 * A helper class with static properties and methods
 */
object Util {
    const val LOG_TAG = "BackgroundThread"
    const val MESSAGE_ID = 1
    const val MESSAGE_BODY = "MESSAGE_BODY"
    const val EMPTY_MESSAGE = "<EMPTY_MESSAGE>"
    val readableTime: String
        get() {
            val sdf = SimpleDateFormat("HH:mm:ss.SSS")
            return sdf.format(Date())
        }

    fun createMessage(id: Int, dataString: String?): Message {
        val bundle = Bundle()
        bundle.putString(MESSAGE_BODY, dataString)
        val message = Message()
        message.what = id
        message.data = bundle
        return message
    }
}