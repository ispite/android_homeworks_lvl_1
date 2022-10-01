package ru.skillbox.a28_32_scopedstorage.presentation.main.download

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.skillbox.a28_32_scopedstorage.databinding.FragmentDownloadFileBinding
import ru.skillbox.a28_32_scopedstorage.utils.ViewBindingBottomSheetDialogFragment
import timber.log.Timber

class DownloadFragment :
    ViewBindingBottomSheetDialogFragment<FragmentDownloadFileBinding>(FragmentDownloadFileBinding::inflate) {

    private val args: DownloadFragmentArgs by navArgs()
    private val viewModel: DownloadVideoViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
    }

    private fun bindViewModel() {
        binding.titleEditText.setText(args.uri?.lastPathSegment?.dropWhile { it != '/' } ?: "")
        binding.titleTextField.isEnabled = args.fileNameEnabled
        viewModel.loading.observe(viewLifecycleOwner, ::setLoading)

        if (args.fileNameEnabled) {
            binding.downloadButton.setOnClickListener {
                val url = binding.urlTextField.editText?.text?.toString().orEmpty()
                val title = binding.titleTextField.editText?.text?.toString().orEmpty()
                viewModel.downloadVideo(title, url)
            }
        } else {
            binding.downloadButton.setOnClickListener {
                val url = binding.urlTextField.editText?.text?.toString().orEmpty()
                Timber.d("another logic")
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    "URL",
                    Pair(args.uri, url)
                )
                findNavController().navigateUp()
            }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
        binding.downloadGroup.isVisible = isLoading.not()
    }
}