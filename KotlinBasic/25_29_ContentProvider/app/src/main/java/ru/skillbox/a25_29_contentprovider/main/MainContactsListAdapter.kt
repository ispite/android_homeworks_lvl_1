package ru.skillbox.a25_29_contentprovider.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_contact.view.*
import ru.skillbox.a25_29_contentprovider.R
import ru.skillbox.a25_29_contentprovider.data.Contact

class MainContactsListAdapter() : RecyclerView.Adapter<MainContactsListAdapter.ContactHolder>() {

    private var contactList: List<Contact> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        val currentItem = contactList[position]
        holder.contactName.text = currentItem.name
        //holder.contactNumber.text = currentItem.phones
    }

    override fun getItemCount(): Int = contactList.count()

    fun setContactsList(contactsList: List<Contact>) {
        this.contactList = contactsList
    }

    class ContactHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val contactName: TextView = itemView.contactNameTextView
        //val contactNumber: TextView = itemView.contactNumberTextView
    }
}