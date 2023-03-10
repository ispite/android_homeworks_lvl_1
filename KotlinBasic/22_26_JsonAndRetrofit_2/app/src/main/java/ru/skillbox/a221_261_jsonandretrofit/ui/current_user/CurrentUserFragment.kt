package ru.skillbox.a221_261_jsonandretrofit.ui.current_user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_profile.*
import ru.skillbox.a221_261_jsonandretrofit.R
import ru.skillbox.a221_261_jsonandretrofit.utils.autoCleared

class CurrentUserFragment : Fragment(R.layout.fragment_profile) {

    private val viewModel: CurrentUserViewModel by viewModels()

    private var userAdapter: UserAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        bindViewModel()
    }

    private fun initList() {
        userAdapter = UserAdapter()
        userList.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun bindViewModel() {
        viewModel.userList.observe(viewLifecycleOwner, Observer { userAdapter.items = it })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { enableControls(it.not()) })
        searchButton.setOnClickListener {
            viewModel.search(
                query = searchInput.text.toString()
            )

        }

        getButton.setOnClickListener {
            viewModel.getAuthenticatedUser()
        }

    }

    private fun enableControls(enable: Boolean) {
        searchInput.isEnabled = enable
        searchButton.isEnabled = enable
    }
}