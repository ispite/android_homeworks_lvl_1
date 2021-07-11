package ru.skillbox.viewandlayout_10

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_third_deeplink.*

class ThirdActivity : AppCompatActivity(R.layout.activity_third_deeplink) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleIntentData()
    }

    //https://skillbox.ru/course/profession-android-developer-2021/

    private fun handleIntentData() {
        intent.data?.host?.let { pathName ->
            //val path = lastSegmentName.joinToString()
            deepLinkPage.text = pathName
        }
    }
}