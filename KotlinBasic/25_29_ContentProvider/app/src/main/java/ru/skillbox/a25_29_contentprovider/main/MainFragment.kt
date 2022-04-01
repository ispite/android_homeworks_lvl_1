package ru.skillbox.a25_29_contentprovider.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_main.*
import ru.skillbox.a25_29_contentprovider.R
import ru.skillbox.a25_29_contentprovider.utils.autoCleared


class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel by viewModels<MainViewModel>()
    private var contactsAdapter: MainContactsListAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        bindViewModel()

        Handler(Looper.getMainLooper()).post{

        }
    }

    private fun initList() {
        contactsAdapter = MainContactsListAdapter()
        with(contactListRecyclerView) {
            adapter = contactsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun bindViewModel() {
        viewModel.contactsList.observe(viewLifecycleOwner) { contactsAdapter.setContactsList(it) }
    }
}