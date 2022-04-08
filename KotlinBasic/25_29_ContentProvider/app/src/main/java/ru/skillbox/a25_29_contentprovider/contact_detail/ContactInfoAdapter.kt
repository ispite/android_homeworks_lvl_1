package ru.skillbox.a25_29_contentprovider.contact_detail

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.skillbox.a25_29_contentprovider.data.ContactInfo

class ContactInfoAdapter():AsyncListDifferDelegationAdapter<ContactInfo>(ContactInfoDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(EmailAdapterDelegate())
            .addDelegate(PhoneAdapterDelegate())
    }

    class ContactInfoDiffUtilCallback : DiffUtil.ItemCallback<ContactInfo>() {
        override fun areItemsTheSame(oldItem: ContactInfo, newItem: ContactInfo): Boolean {
            return when {
                oldItem is ContactInfo.Phone && newItem is ContactInfo.Phone -> oldItem.id == newItem.id
                oldItem is ContactInfo.Email && newItem is ContactInfo.Email -> oldItem.id == newItem.id
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: ContactInfo, newItem: ContactInfo): Boolean {
            return oldItem == newItem
        }

    }

}