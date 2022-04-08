package ru.skillbox.a25_29_contentprovider.contact_detail

import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import ru.skillbox.a25_29_contentprovider.R
import ru.skillbox.a25_29_contentprovider.data.ContactInfo
import ru.skillbox.a25_29_contentprovider.utils.inflate

class PhoneAdapterDelegate() :
    AbsListItemAdapterDelegate<ContactInfo.Phone, ContactInfo, PhoneAdapterDelegate.PhoneHolder>() {

    override fun isForViewType(
        item: ContactInfo,
        items: MutableList<ContactInfo>,
        position: Int
    ): Boolean {
        return item is ContactInfo.Phone
    }

    override fun onCreateViewHolder(parent: ViewGroup): PhoneHolder {
        return PhoneHolder(
            parent.inflate(R.layout.item_phone)
        )
    }

    override fun onBindViewHolder(
        item: ContactInfo.Phone,
        holder: PhoneHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class PhoneHolder(
        view: View
    ) : BaseContactInfoHolder(view) {

        fun bind(contactInfo: ContactInfo.Phone) {
            bindMainInfo(contactInfo.id)
        }
    }
}