package ru.skillbox.a25_29_contentprovider.contact_detail

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseContactInfoHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var currentId: Long? = null

    protected fun bindMainInfo(
        id: Long
    ) {
        currentId = id
    }
}