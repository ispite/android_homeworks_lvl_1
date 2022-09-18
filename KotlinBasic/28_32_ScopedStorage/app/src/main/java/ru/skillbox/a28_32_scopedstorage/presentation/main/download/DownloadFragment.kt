package ru.skillbox.a28_32_scopedstorage.presentation.main.download

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import ru.skillbox.a28_32_scopedstorage.databinding.FragmentDownloadFileBinding
import ru.skillbox.a28_32_scopedstorage.utils.ViewBindingBottomSheetDialogFragment

class DownloadFragment : //BottomSheetDialogFragment() {
    ViewBindingBottomSheetDialogFragment<FragmentDownloadFileBinding>(FragmentDownloadFileBinding::inflate) {

    private val viewModel: DownloadVideoViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()

    }

    private fun bindViewModel() {
        binding.downloadButton.setOnClickListener {
            val url = binding.urlTextField.editText?.text?.toString().orEmpty()
            val title = binding.titleTextField.editText?.text?.toString().orEmpty()
            viewModel.downloadVideo(title, url)
        }
        viewModel.loading.observe(viewLifecycleOwner, ::setLoading)
    }

    private fun setLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
        binding.downloadGroup.isVisible = isLoading.not()
    }

}