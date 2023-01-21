package ru.skillbox.dependency_injection.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.skillbox.dependency_injection.R
import ru.skillbox.dependency_injection.app.App

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
//        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }
}