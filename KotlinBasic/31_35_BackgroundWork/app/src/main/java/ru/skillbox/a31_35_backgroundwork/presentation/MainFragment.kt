package ru.skillbox.a31_35_backgroundwork.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.work.WorkInfo
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.skillbox.a31_35_backgroundwork.R
import ru.skillbox.a31_35_backgroundwork.databinding.FragmentMainBinding
import timber.log.Timber

class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding by viewBinding(FragmentMainBinding::bind)
    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        binding.downloadButton.setOnClickListener { startDownload() }
    }

    private fun startDownload() {
        val urlToDownload = binding.inputUrl.text.toString()



        viewModel.startDownload(urlToDownload)
/*        val workRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .build()

        WorkManager.getInstance(requireContext())
            .enqueue(workRequest)*/
    }

    private fun bindViewModel() {
        viewModel.workInfo.observe(viewLifecycleOwner) { handleWorkInfo(it) }
    }

    private fun handleWorkInfo(workInfo: WorkInfo) {
        Timber.d("handleWorkInfo state=${workInfo.state}")
        val isFinished = workInfo.state.isFinished
        binding.downloadButton.isEnabled = isFinished
        binding.downloadProgressBar.isVisible = !isFinished
    }
}