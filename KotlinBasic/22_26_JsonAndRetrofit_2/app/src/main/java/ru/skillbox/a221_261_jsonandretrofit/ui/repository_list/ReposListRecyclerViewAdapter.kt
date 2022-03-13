package ru.skillbox.a221_261_jsonandretrofit.ui.repository_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_repository.view.*
import ru.skillbox.a221_261_jsonandretrofit.R
import ru.skillbox.a221_261_jsonandretrofit.data.RemoteRepository

class ReposListRecyclerViewAdapter(
    private val onItemClick: (name: String, description: String?, login: String) -> Unit
) :
    RecyclerView.Adapter<ReposListRecyclerViewAdapter.RepositoryHolder>() {

    private var reposList: List<RemoteRepository> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_repository, parent, false)
        return RepositoryHolder(itemView, onItemClick)
    }

    override fun onBindViewHolder(holder: RepositoryHolder, position: Int) {
        val currentItem = reposList[position]
        holder.reposNameTextView.text = currentItem.name
        holder.reposDescriptionTextView.text = currentItem.description
        holder.login = currentItem.login.login
    }

    override fun getItemCount(): Int = reposList.size

    fun setReposList(reposList: List<RemoteRepository>) {
        this.reposList = reposList
        notifyDataSetChanged()
    }

    class RepositoryHolder(
        itemView: View,
        onItemClick: (name: String, description: String?, login: String) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        val reposNameTextView: TextView = itemView.reposNameTextView
        val reposDescriptionTextView: TextView = itemView.reposDescriptionTextView
        var login: String = ""

        init {
            itemView.setOnClickListener {
                onItemClick(
                    reposNameTextView.text.toString(),
                    reposDescriptionTextView.text.toString(),
                    login
                )
            }
        }
    }
}