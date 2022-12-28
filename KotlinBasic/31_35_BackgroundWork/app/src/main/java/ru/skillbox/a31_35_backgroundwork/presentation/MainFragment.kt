package ru.skillbox.a31_35_backgroundwork.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
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
        viewModel.observeWork()
        bindViewModel()
        binding.downloadButton.setOnClickListener { startDownload() }
        binding.cancelDownload.setOnClickListener {
            Timber.d("cancelDownload")
            cancelWork()
        }
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
        binding.errorTextView.isVisible = !isFinished
        Timber.d("workInfo.state=${workInfo.state}")
        when (workInfo.state) {
/*            WorkInfo.State.BLOCKED -> {
                binding.errorTextView.text = "Ждём подключение к Wi-fi"
            } */
            WorkInfo.State.ENQUEUED -> {
                binding.errorTextView.text = "Ждём подключение к Wi-fi"
                binding.cancelDownload.isVisible = true
            }
            WorkInfo.State.RUNNING -> {
                binding.errorTextView.isVisible = false
                binding.cancelDownload.isVisible = true
            }
            WorkInfo.State.FAILED -> {
                //TODO
                // как сделать пункт 5.3 - FAILED?
                binding.retryDownload.isVisible = true
                binding.cancelDownload.isVisible = false
            }
            WorkInfo.State.SUCCEEDED -> {
                Toast.makeText(
                    requireContext(),
                    "work finished with state = ${workInfo.state}",
                    Toast.LENGTH_SHORT
                ).show()
                binding.cancelDownload.isVisible = false
            }
            else -> {
                binding.cancelDownload.isVisible = false
            }
        }
    }

    //TODO доделать 7й пункт: отмена задачи
    private fun cancelWork() {
        viewModel.cancelWork()
    }
}