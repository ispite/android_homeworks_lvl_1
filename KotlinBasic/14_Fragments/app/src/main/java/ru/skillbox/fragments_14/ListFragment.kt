package ru.skillbox.fragments_14


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ListFragment : Fragment(R.layout.fragment_list), CustomRecyclerAdapter.OnItemClickListener {

    private val exampleList = generateDummyList(30)
    private val adapter = CustomRecyclerAdapter(exampleList, this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_list, container, false)
        val listView = rootView.findViewById(R.id.recyclerView) as RecyclerView
        listView.adapter = adapter
        listView.layoutManager = LinearLayoutManager(activity)
        listView.setHasFixedSize(true)
        return rootView
    }

    override fun onItemClick(position: Int) {
        //НЕ РАБОТАЕТ в режиме телефона, НЕ ПОНИМАЮ ПОЧЕМУ?
        Toast.makeText(activity, "Item ${position + 1}", Toast.LENGTH_SHORT).show()
        //toast("SDFSDFSDF")
        ////////////////////////////////////
        adapter.notifyItemChanged(position)
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
            .replace(R.id.containerFragmentMain, DetailFragment.newInstance(position))
            .addToBackStack(null)
            .commit()
    }

    private fun toast(text: String) {
        Toast.makeText(this.activity, text, Toast.LENGTH_LONG).show()
    }

    private fun generateDummyList(size: Int): List<ExampleItem> {
        val list = ArrayList<ExampleItem>()
        for (i in 1 until size) {
            val drawable = when (i % 3) {
                1 -> R.drawable.ic_android
                2 -> R.drawable.ic_audio
                else -> R.drawable.ic_sun
            }
            val item = ExampleItem(drawable, "Item $i", "Line 2")
            list += item
        }
        return list
    }
}

//https://guides.codepath.com/android/Flexible-User-Interfaces
//https://guides.codepath.com/android/creating-and-using-fragments

//https://codinginflow.com/tutorials/android/simple-recyclerview-kotlin/part-4-click-handler
//https://www.youtube.com/watch?v=wKFJsrdiGS8

//http://developer.alexanderklimov.ru/android/views/recyclerview-kot.php
//https://devcolibri.com/unit/%D1%83%D1%80%D0%BE%D0%BA-10-%D1%80%D0%B0%D0%B1%D0%BE%D1%82%D0%B0-%D1%81-recyclerview-%D0%BD%D0%B0-%D0%BF%D1%80%D0%B8%D0%BC%D0%B5%D1%80%D0%B5-tweetsrecyclerview-2/
//https://overcoder.net/q/85120/%D0%BF%D1%80%D0%BE%D1%81%D1%82%D0%BE%D0%B9-%D0%BF%D1%80%D0%B8%D0%BC%D0%B5%D1%80-android-recyclerview