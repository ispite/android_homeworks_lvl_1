package ru.skillbox.a30_34_flow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.skillbox.a30_34_flow.data.MovieType

class MainViewModel : ViewModel() {
    val editTextFlow = MutableLiveData<String>()
    val radioGroupFlow = MutableLiveData<MovieType>()
}