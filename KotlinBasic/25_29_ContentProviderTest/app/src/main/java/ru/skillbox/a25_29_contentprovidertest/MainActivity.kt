package ru.skillbox.a25_29_contentprovidertest

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class MainActivity(/*application: Application*/) : AppCompatActivity() {
    //val context = applicationContext
    //val repository = MainRepository(applicationContext)
    private val myActivityScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val repository = MainRepository(applicationContext)
        myActivityScope.launch {
            repository.saveRandomCourse()
        }
    }
}