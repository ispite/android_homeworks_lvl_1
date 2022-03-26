package ru.skillbox.a24_28_files.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.skillbox.a24_28_files.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment())
                .commit()
        }
    }
}