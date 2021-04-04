package ru.skillbox.viewandlayout_10

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var flagEmail = false
        var flagPassword = false
        var flagCheckBox = false

        editTextTextEmailAddress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //loginButton.isEnabled= s?.let { it.isNotEmpty() } ?: false
                flagEmail = s?.isNotEmpty() ?: false
                loginButton.isEnabled = flagCheckBox && flagEmail && flagPassword
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        editTextTextPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //loginButton.isEnabled= s?.let { it.isNotEmpty() } ?: false
                flagPassword = s?.isNotEmpty() ?: false
                loginButton.isEnabled = flagCheckBox && flagEmail && flagPassword
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
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

        Handler().postDelayed({
            progressBar.visibility = View.GONE
            loginButton.isEnabled = true
            editTextTextEmailAddress.isEnabled = true
            editTextTextPassword.isEnabled = true
            checkBox.isEnabled = true
        }, 2000)
    }
}