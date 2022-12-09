package ru.skillbox.a31_35_backgroundwork.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.skillbox.a31_35_backgroundwork.DownloadWorker
import ru.skillbox.a31_35_backgroundwork.R
import ru.skillbox.a31_35_backgroundwork.data.DownloadWorkerRepository
import ru.skillbox.a31_35_backgroundwork.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding by viewBinding(FragmentMainBinding::bind)
    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
}