package ru.skillbox.fragments_14


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ListFragment:Fragment(R.layout.fragment_list), CustomRecyclerAdapter.OnItemClickListener {

    /*override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val recyclerView: RecyclerView = view?.findViewById(R.id.recyclerView) ?: true
//        //val recyclerView: RecyclerView = inflater.inflate(R.layout.recyclerview_item)
//        recyclerView.layoutManager = LinearLayoutManager(context)

        *//*val rootView = inflater.inflate(R.layout.recyclerview_item, container, false)
        video_recyclerview = rootView.findViewById(R.id.id_of_video_recyclerview) as RecyclerView // Add this
        video_recyclerview.layoutManager = LinearLayoutManager(activity)
        video_recyclerview.adapter = MainAdapter()
        return rootView*//*



        //val rootView: View = inflater.inflate(R.layout.fragment_list, null)
        //REFERENCE
        //rv = rootView.findViewById<View>(R.id.intergalactic_RV) as RecyclerView
        //LAYOUT MANAGER
        //rv.setLayoutManager(LinearLayoutManager(activity))
        //ADAPTER
        //rv.setAdapter(MyAdapter(activity, spacecrafts))
        //return rootView

        return super.onCreateView(inflater, container, savedInstanceState)
    }*/

    val exampleList = fillList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_list, container, false)
        val videoRecyclerView = rootView.findViewById(R.id.recyclerView) as RecyclerView
        videoRecyclerView.layoutManager = LinearLayoutManager(activity)
        videoRecyclerView.adapter = CustomRecyclerAdapter(exampleList, this)
        return rootView
    }

    override fun onItemClick(position: Int) {
        toast("Item $position clicked")
        val clickedItem = exampleList[position]
        //clickedItem
        //adapter.notifyItemChanged
    }

    private fun fillList(): List<String> {
        val data = mutableListOf<String>()
        (1..30).forEach { i -> data.add("$i элемент") }
        return data
    }

    private fun toast(text: String) {
        //Toast.makeText(this, text, 123).show()
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
    }
}

//https://codinginflow.com/tutorials/android/simple-recyclerview-kotlin/part-4-click-handler
//https://www.youtube.com/watch?v=wKFJsrdiGS8

//http://developer.alexanderklimov.ru/android/views/recyclerview-kot.php
//https://devcolibri.com/unit/%D1%83%D1%80%D0%BE%D0%BA-10-%D1%80%D0%B0%D0%B1%D0%BE%D1%82%D0%B0-%D1%81-recyclerview-%D0%BD%D0%B0-%D0%BF%D1%80%D0%B8%D0%BC%D0%B5%D1%80%D0%B5-tweetsrecyclerview-2/
//https://overcoder.net/q/85120/%D0%BF%D1%80%D0%BE%D1%81%D1%82%D0%BE%D0%B9-%D0%BF%D1%80%D0%B8%D0%BC%D0%B5%D1%80-android-recyclerview