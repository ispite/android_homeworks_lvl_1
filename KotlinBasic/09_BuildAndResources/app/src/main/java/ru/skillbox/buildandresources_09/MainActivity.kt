package ru.skillbox.buildandresources_09

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textView)
        textView.text = """
            Build type = ${BuildConfig.BUILD_TYPE}
            Flavor = ${BuildConfig.FLAVOR}
            Version Code = ${BuildConfig.VERSION_CODE}
            Version Name = ${BuildConfig.VERSION_NAME}
            Applecation ID = ${BuildConfig.APPLICATION_ID}
        """
    }
}