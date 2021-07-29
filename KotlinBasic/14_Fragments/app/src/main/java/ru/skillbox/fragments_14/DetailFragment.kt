package ru.skillbox.fragments_14

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment:Fragment(R.layout.fragment_detail) {

    companion object {
        private const val KEY_NUMBER = "key_number"
        fun newInstance(number: Int): DetailFragment {
            return DetailFragment().withArguments{
                putInt(KEY_NUMBER, number)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val list = generateDetailDummyList(30)
        val item = list[1]
        //imageView3.setImageResource(R.drawable.ic_android)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val list = generateDetailDummyList(30)
//        val item = list[1]
//        imageDetailView.setImageResource(R.drawable.ic_android)
        //imageView3.setImageResource(R.drawable.ic_android)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //val textView = requireView().findViewById<TextView>(R.id.textDetailView1)
        textDetailView1.text = requireArguments().getInt(KEY_NUMBER).toString()
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