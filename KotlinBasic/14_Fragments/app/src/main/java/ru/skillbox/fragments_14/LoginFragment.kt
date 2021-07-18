package ru.skillbox.fragments_14

import android.app.PendingIntent.getActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.google.android.material.internal.ContextUtils
import com.google.android.material.internal.ContextUtils.getActivity
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment:Fragment(R.layout.fragment_login) {

    /*fun onCreateView(
        inflater: MainActivity.Companion,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }*/



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

//        override fun onClick(v:View?){
//
//        }

        loginButton.setOnClickListener {
            loading()

            //activity.let {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment())
                .commit()



            /*activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, MainFragment())
                ?.commit()*/

            toast("ACTIVITY")

            //}
//            onCreateView(activity) {
//                supportFragmentManager.beginTransaction()
//                    .add(R.id.container, LoginFragment())
//                    .commit()
//            }



//            public void onClick(View v) {
//                Fragment fragment = new tasks();
//                FragmentManager fragmentManager = getActivity ().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager . beginTransaction ();
//                fragmentTransaction.replace(R.id.content_frame, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//            }
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

        /*Handler(Looper.getMainLooper()).postDelayed({
            progressBar.visibility = View.GONE
            loginButton.isEnabled = true
            editTextTextEmailAddress.isEnabled = true
            editTextTextPassword.isEnabled = true
            checkBox.isEnabled = true
            scrollView.isEnabled = true
        }, 2000)*/
    }

    companion object {
        /*fun successLogin(key: Boolean): LoginFragment{

        }*/
    }

    private fun toast(text: String) {
        //Toast.makeText(this, text, 123).show()
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
    }
}