package ru.skillbox.a221_261_jsonandretrofit.ui.repository_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_repository_details.*
import ru.skillbox.a221_261_jsonandretrofit.R

class RepositoryDetailsFragment : Fragment(R.layout.fragment_repository_details) {
    private val args: RepositoryDetailsFragmentArgs by navArgs()

    private val viewModel: RepositoryDetailsViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        checkRepoStared(args.reposOwner, args.reposName)

        reposNameTextViewDetail.text = args.reposName
        reposDescriptionTextViewDetail.text = args.reposDescription

        giveRemoveStar.setOnClickListener {
            viewModel.starUnstarRepo(
                args.reposOwner,
                args.reposName
            )
        }
    }

    private fun checkRepoStared(owner: String, repo: String) {
        viewModel.checkRepoStared(owner, repo)
        viewModel.repoStared.observe(viewLifecycleOwner) {
            if (it) {
                //giveRemoveStar.setImageDrawable(R.drawable.ic_star_filled.toDrawable())
                giveRemoveStar.setImageResource(R.drawable.ic_star_filled)
            } else {
                //giveRemoveStar.setImageDrawable(R.drawable.ic_star_border.toDrawable())
                giveRemoveStar.setImageResource(R.drawable.ic_star_border)
            }
        }
    }
}