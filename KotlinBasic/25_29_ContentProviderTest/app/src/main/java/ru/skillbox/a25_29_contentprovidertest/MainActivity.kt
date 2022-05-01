package ru.skillbox.a25_29_contentprovidertest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainActivity(/*application: Application*/) : AppCompatActivity() {
    //val context = applicationContext
    //val repository = MainRepository(applicationContext)
    private val myActivityScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val repository = MainRepository(applicationContext)
/*        myActivityScope.launch {
            //repository.saveRandomCourse()
        }*/

        saveBunchCoursesButton.setOnClickListener {
            myActivityScope.launch {
                repository.saveBunchRandomCourses(5)
            }
        }

        listCoursesButton.setOnClickListener {
            myActivityScope.launch {
                Log.d("MainActivity", "onCreate: ${repository.getAllCourses()}")
                //repository.getAllCourses()
            }
        }
    }
}