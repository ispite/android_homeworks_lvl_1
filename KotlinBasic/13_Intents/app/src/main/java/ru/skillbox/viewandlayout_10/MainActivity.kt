package ru.skillbox.viewandlayout_10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.core.widget.doOnTextChanged
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val tag = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DebugLogger.d(tag, "onCreate was called")


        var flagEmail = false
        var flagPassword = false
        var flagCheckBox = false

        editTextTextEmailAddress.doOnTextChanged { text, _, _, _ ->
            flagEmail = text?.isNotEmpty() ?: false
            loginButton.isEnabled = flagCheckBox && flagEmail && flagPassword
        }

        editTextTextEmailAddress.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (!editTextTextEmailAddress.text.contains("@")) {
                    textView.text = "Логин должен содержать @ !"
                    flagEmail = false
                } else {
                    textView.text = null
                    flagEmail = true
                }
            }
        }

        editTextTextPassword.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (editTextTextPassword.text.length < 8) {
                    textView2.text = "Пароль должен быть длинее 7 символов!"
                    flagPassword = false
                } else {
                    textView2.text = null
                    flagPassword = true
                }
            }
        }

        editTextTextPassword.doOnTextChanged { text, _, _, _ ->
            flagPassword = text?.isNotEmpty() ?: false
            loginButton.isEnabled = flagCheckBox && flagEmail && flagPassword
        }

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            flagCheckBox = isChecked
            loginButton.isEnabled = flagCheckBox && flagEmail && flagPassword
        }

        loginButton.setOnClickListener {
            loading()
        }

        ANR_Button.setOnClickListener {
            Thread.sleep(6000)
        }

        Glide.with(this)
            .load("https://i.pinimg.com/736x/50/df/34/50df34b9e93f30269853b96b09c37e3b.jpg")
            .into(imageView2)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        textView.text = savedInstanceState.getString(KEY_TEXTVIEW)
        textView2.text = savedInstanceState.getString(KEY_TEXTVIEW2)
    }

    override fun onStart() {
        super.onStart()
        DebugLogger.v(tag, "onStart was called")
    }

    override fun onResume() {
        super.onResume()
        DebugLogger.d(tag, "onResume was called")
    }

    override fun onPause() {
        super.onPause()
        DebugLogger.i(tag, "onPause was called")
    }

    override fun onStop() {
        super.onStop()
        DebugLogger.e(tag, "onStop was called")
    }

    override fun onDestroy() {
        super.onDestroy()
        DebugLogger.v(tag, "onDestroy was called")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //outState.putString(KEY_TEXTVIEW, textView.text.toString())
        outState.putString(KEY_TEXTVIEW2, textView2.text.toString())


        //Странный блок с 4м пунктом домашней работы
        val formState1 = FormState(true, textView.text.toString())
        outState.putString(KEY_TEXTVIEW, formState1.message)
    }

    companion object {
        private const val KEY_TEXTVIEW = "textView"
        private const val KEY_TEXTVIEW2 = "textView2"
    }

    private fun loading() {
        progressBar.visibility = View.VISIBLE
        loginButton.isEnabled = false
        editTextTextEmailAddress.isEnabled = false
        editTextTextPassword.isEnabled = false
        checkBox.isEnabled = false
        scrollView.isEnabled = false

        Handler(Looper.getMainLooper()).postDelayed({
            progressBar.visibility = View.GONE
            loginButton.isEnabled = true
            editTextTextEmailAddress.isEnabled = true
            editTextTextPassword.isEnabled = true
            checkBox.isEnabled = true
            scrollView.isEnabled = true
        }, 2000)
    }
}

data class FormState(
    val valid: Boolean,
    val message: String
) {
}

object DebugLogger {
    fun v(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, message)
        }
    }

    fun d(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message)
        }
    }

    fun i(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, message)
        }
    }

    fun e(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message)
        }
    }
}