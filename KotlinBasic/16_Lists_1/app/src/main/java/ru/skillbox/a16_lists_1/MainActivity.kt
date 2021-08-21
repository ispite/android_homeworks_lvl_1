package ru.skillbox.a16_lists_1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

//https://ekb.explorer-russia.ru/gallery/auto/modification/3615.jpg
//http://autodd.b-cdn.net/wp-content/uploads/2019/07/volvo-s60_13.jpg
//https://www.carpixel.net/w/e42a7b718cbd375a65f82c97de695dd3/bmw-3-series-wallpaper-hd-81853.jpg
//https://pbs.twimg.com/media/DytoztBWoAAbR2r.jpg

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, VehicleListFragment())
                .commit()
        }
    }

}