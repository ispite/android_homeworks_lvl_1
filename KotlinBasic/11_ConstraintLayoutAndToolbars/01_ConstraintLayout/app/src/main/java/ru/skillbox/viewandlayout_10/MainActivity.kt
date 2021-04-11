package ru.skillbox.viewandlayout_10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.widget.doOnTextChanged
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var flagEmail = false
        var flagPassword = false
        var flagCheckBox = false

        editTextTextEmailAddress.doOnTextChanged { text, _, _, _ ->
            flagEmail = text?.isNotEmpty() ?: false
            loginButton.isEnabled = flagCheckBox && flagEmail && flagPassword
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

        Glide.with(this)
                .load("https://i.pinimg.com/736x/50/df/34/50df34b9e93f30269853b96b09c37e3b.jpg")
                .into(imageView2)
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