package ru.skillbox.a21_networking

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

/*        val cinematicTypes = resources.getStringArray(R.array.cinematic_types)

        val adapter = ArrayAdapter(this, R.layout.item_dropdown, cinematicTypes)
        (menu.editText as? AutoCompleteTextView)?.setAdapter(adapter)*/

        //val autocompleteTV = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        // set adapter to the autocomplete tv to the arrayAdapter
        //autocompleteTV.setAdapter(adapter)
    }
}