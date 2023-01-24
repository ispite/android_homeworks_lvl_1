package ru.skillbox.a34_38_Release_Flow.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.RadioGroup
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber

fun EditText.textChangedFlow(): Flow<String> {
    return callbackFlow<String> {
        val textChangedListener = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                    sendBlocking(p0?.toString().orEmpty())
                trySendBlocking(charSequence?.toString().orEmpty())
            }

            override fun afterTextChanged(p0: Editable?) {}
        }
        this@textChangedFlow.addTextChangedListener(textChangedListener)
        awaitClose {
            Timber.d("awaitClose ")
            this@textChangedFlow.removeTextChangedListener(textChangedListener)
        }
    }
}

fun RadioGroup.radioGroupChangedFlow(): Flow<Int> {
    return callbackFlow<Int> {
        val radioGroupChangedListener =
            RadioGroup.OnCheckedChangeListener { _, viewId -> trySendBlocking(viewId) }
        this@radioGroupChangedFlow.setOnCheckedChangeListener(radioGroupChangedListener)
        awaitClose {
            Timber.d("awaitClose radioGroup")
            this@radioGroupChangedFlow.setOnCheckedChangeListener(null)
        }
    }
}