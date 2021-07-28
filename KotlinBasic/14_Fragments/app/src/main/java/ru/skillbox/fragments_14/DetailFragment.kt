package ru.skillbox.fragments_14

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment:Fragment(R.layout.fragment_detail) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val list = generateDetailDummyList(30)
        val item = list[1]
        imageView3.setImageResource(item.imageResource)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    /*class DetailHolder(item: View) {
        val imageView: ImageView =itemView.image_view
    }*/


    private fun generateDetailDummyList(size: Int): List<ExampleItem> {
        val list = ArrayList<ExampleItem>()
        for (i in 0 until size) {
            val drawable = when (i % 3) {
                0 -> R.drawable.ic_android
                1 -> R.drawable.ic_audio
                else -> R.drawable.ic_sun
            }
            val item = ExampleItem(drawable, "Item $i", "Line 2")
            list += item
        }
        return list
    }
}