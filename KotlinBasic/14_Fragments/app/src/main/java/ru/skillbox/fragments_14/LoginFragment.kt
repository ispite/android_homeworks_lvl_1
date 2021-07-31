package ru.skillbox.fragments_14

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                .replace(R.id.container, MainFragment())
                .commit()
        }

        ANR_Button.setOnClickListener {
            Thread.sleep(6000)
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
    }

    private fun toast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
    }
}