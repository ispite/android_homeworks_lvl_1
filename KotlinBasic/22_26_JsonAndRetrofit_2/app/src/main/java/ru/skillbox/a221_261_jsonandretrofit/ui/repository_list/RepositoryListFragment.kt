package ru.skillbox.a221_261_jsonandretrofit.ui.repository_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_repository_list.*
import ru.skillbox.a221_261_jsonandretrofit.R
import ru.skillbox.a221_261_jsonandretrofit.data.AuthConfig

class RepositoryListFragment : Fragment(R.layout.fragment_repository_list) {

    private val viewModel: RepositoryListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() {
        val reposAdapter = ReposListRecyclerViewAdapter { reposName, reposDescription, login ->
            val action =
                RepositoryListFragmentDirections.actionRepositoryListFragmentToRepositoryDetailsFragment(
                    reposName,
                    reposDescription,
                    login
                )

            findNavController().navigate(action)
        }

        with(reposListRecyclerView) {
            adapter = reposAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel.reposList.observe(viewLifecycleOwner) {
            reposAdapter.setReposList(it)
        }
        getReposButton.setOnClickListener { viewModel.getAuthenticatedRepositories() }
        getStaredReposButton.setOnClickListener { viewModel.getStarredRepos(AuthConfig.USERNAME) }

    }
}