package ru.skillbox.a25_29_contentprovider.contact_detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_contact_detail.*
import ru.skillbox.a25_29_contentprovider.R

class ContactDetailFragment:Fragment(R.layout.fragment_contact_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() {
        val contactInfoAdapter = ContactInfoAdapter()

        with(contactInfoListRecyclerView) {
            adapter = contactInfoAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }
}