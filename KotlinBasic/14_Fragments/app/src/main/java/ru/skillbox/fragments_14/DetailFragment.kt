package ru.skillbox.fragments_14

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment(R.layout.fragment_detail) {

    companion object {
        private const val KEY_NUMBER = "key_number"
        fun newInstance(number: Int): DetailFragment {
            return DetailFragment().withArguments {
                putInt(KEY_NUMBER, number)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = generateDetailDummyList(30)
        val currentItem = list[requireArguments().getInt(KEY_NUMBER)]
        imageDetailView.setImageResource(currentItem.imageResource)
        textDetailView1.text = currentItem.text1
        textDetailView2.text = currentItem.text2
    }

    private fun generateDetailDummyList(size: Int): List<ExampleItem> {
        val list = ArrayList<ExampleItem>()
        for (i in 1 until size) {
            val drawable = when (i % 3) {
                1 -> R.drawable.ic_android
                2 -> R.drawable.ic_audio
                else -> R.drawable.ic_sun
            }
            val item = ExampleItem(
                drawable, "Item $i", "Detailed information about Item № $i: \n" +
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mollis urna " +
                        "sed mi mollis aliquam vitae sed lacus. Morbi in enim vel lacus faucibus " +
                        "hendrerit. Quisque turpis tellus, egestas tincidunt viverra vel, molestie " +
                        "at arcu. Curabitur eget facilisis ipsum. In hac habitasse platea dictumst. " +
                        "Donec in vestibulum est. Sed eget. "
            )
            list += item
        }
        return list
    }
}