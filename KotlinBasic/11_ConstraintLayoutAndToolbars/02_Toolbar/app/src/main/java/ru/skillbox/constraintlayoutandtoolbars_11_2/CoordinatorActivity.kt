package ru.skillbox.constraintlayoutandtoolbars_11_2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_coordinator.*

class CoordinatorActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator)
        toolbar.setNavigationOnClickListener {
            Toast.makeText(this, "sada", Toast.LENGTH_LONG).show()
        }
    }
}