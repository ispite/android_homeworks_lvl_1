package ru.skillbox.a24_28_files.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import kotlinx.android.synthetic.main.fragment_main.*
import ru.skillbox.a24_28_files.R

class MainFragment:Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isLoading.observe(viewLifecycleOwner, ::updateLoadingState)
        viewModel.showToast.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Файл ${viewModel.fileName.value} успешно загружен", Toast.LENGTH_SHORT).show()
        }
        viewModel.showToast2.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Файл ${viewModel.fileName.value} был загружен ранее", Toast.LENGTH_SHORT).show()
        }
        viewModel.downloadOnFirstRun()

        downloadFileButton.setOnClickListener {
            viewModel.downloadFile(inputUrlEditText.text.toString())
        }

        inputUrlEditText.doOnTextChanged { text, _, _, _ ->
            val lengthText = text?.length ?: 0
            //downloadFileButton.isEnabled = (lengthText > 0)
        }
    }

    private fun updateLoadingState(isLoading: Boolean) {
        progress_circular.isVisible = isLoading
        downloadFileButton.isEnabled = !isLoading
        inputUrlEditText.isEnabled = !isLoading
    }
}