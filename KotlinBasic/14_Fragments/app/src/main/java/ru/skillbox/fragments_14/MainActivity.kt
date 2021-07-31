package ru.skillbox.fragments_14

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.fragment_login.*

class MainActivity : AppCompatActivity() {

    private val tag = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DebugLogger.d(tag, "onCreate was called")

        showLoginFragment()
    }

    private fun showLoginFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, LoginFragment())
            .commit()
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
        outState.putString(KEY_TEXTVIEW2, textView2.text.toString())
        val formState1 = FormState(true, textView.text.toString())
        outState.putString(KEY_TEXTVIEW, formState1.message)
    }

    companion object {
        private const val KEY_TEXTVIEW = "textView"
        private const val KEY_TEXTVIEW2 = "textView2"
    }

    override fun onBackPressed() {
        // if there is a fragment and the back stack of this fragment is not empty,
        // then emulate 'onBackPressed' behaviour, because in default, it is not working
        val fm: FragmentManager = supportFragmentManager
        for (frag in fm.fragments) {
            if (frag.isVisible) {
                val childFm: FragmentManager = frag.childFragmentManager
                if (childFm.backStackEntryCount > 0) {
                    childFm.popBackStack()
                    return
                }
            }
        }
        super.onBackPressed()
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