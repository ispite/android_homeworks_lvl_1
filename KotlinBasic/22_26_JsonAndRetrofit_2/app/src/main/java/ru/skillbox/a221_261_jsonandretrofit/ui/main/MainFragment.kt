package ru.skillbox.a221_261_jsonandretrofit.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import ru.skillbox.a221_261_jsonandretrofit.R

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkBio()
        currentUserInfoButton.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToCurrentUserFragment())
        }

        repositoryListButton.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToRepositoryListFragment())
        }

        inputBioEdit.doOnTextChanged { text, _, _, _ ->
            changeBioButton.isEnabled = text.toString() != viewModel.userBio.value.toString()
        }

        changeBioButton.setOnClickListener {
            patchBio()
            changeBioButton.isEnabled = false
        }
    }

    private fun checkBio() {
        viewModel.checkBio()
        viewModel.userBio.observe(viewLifecycleOwner) {
            inputBioEdit.setText(it)
        }
        viewModel.errorBio.observe(viewLifecycleOwner) {
            errorBio.text = it
        }
    }

    private fun patchBio() {
        viewModel.patchBio(inputBioEdit.text.toString())
    }
}