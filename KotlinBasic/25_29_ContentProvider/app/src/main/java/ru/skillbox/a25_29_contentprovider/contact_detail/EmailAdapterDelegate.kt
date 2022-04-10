package ru.skillbox.a25_29_contentprovider.contact_detail

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import ru.skillbox.a25_29_contentprovider.R
import ru.skillbox.a25_29_contentprovider.data.ContactInfo
import ru.skillbox.a25_29_contentprovider.utils.inflate

class EmailAdapterDelegate() :
    AbsListItemAdapterDelegate<ContactInfo.Email, ContactInfo, EmailAdapterDelegate.EmailHolder>() {

    override fun isForViewType(
        item: ContactInfo,
        items: MutableList<ContactInfo>,
        position: Int
    ): Boolean {
        return item is ContactInfo.Email
    }

    override fun onCreateViewHolder(parent: ViewGroup): EmailHolder {
        return EmailHolder(
            parent.inflate(R.layout.item_email)
        )
    }

    override fun onBindViewHolder(
        item: ContactInfo.Email,
        holder: EmailHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class EmailHolder(
        view: View
    ) : BaseContactInfoHolder(view) {
        private val email: TextView = view.findViewById(R.id.emailAddress)

        fun bind(contactInfo: ContactInfo.Email) {
            bindMainInfo(contactInfo.id)
            email.text = contactInfo.email
        }
    }
}