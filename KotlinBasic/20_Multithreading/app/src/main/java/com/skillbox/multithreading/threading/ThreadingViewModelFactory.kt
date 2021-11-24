package com.skillbox.multithreading.threading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ThreadingViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ThreadingViewModel::class.java)) {
            ThreadingViewModel() as T
        } else throw IllegalArgumentException("Unknown ViewModel")
    }
}