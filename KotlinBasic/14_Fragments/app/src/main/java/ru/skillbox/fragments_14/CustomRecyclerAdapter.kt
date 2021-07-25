package ru.skillbox.fragments_14

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class CustomRecyclerAdapter(
    private val names: List<String>,
    private val listener: OnItemClickListener
    ):
    RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener{
        val bigText : TextView = itemView.textViewLarge
        val smallText : TextView = itemView.textViewSmall
        //var largeTextView: TextView? = null
        //var smallTextView: TextView? = null

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }

        }

        /*init {
            largeTextView = itemView.findViewById(R.id.textViewLarge)
            smallTextView = itemView.findViewById(R.id.textViewSmall)
        }*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bigText.text = names[position]
        holder.smallText.text = "кот"
    }

    override fun getItemCount() = names.size

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}