package ru.skillbox.viewandlayout_10

import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity(R.layout.activity_second) {
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}