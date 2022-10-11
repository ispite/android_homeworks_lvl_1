package ru.skillbox.a30_34_flow

import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.skillbox.a30_34_flow.data.MovieType
import ru.skillbox.a30_34_flow.databinding.FragmentMainBinding
import ru.skillbox.a30_34_flow.utils.textChangedFlow
import timber.log.Timber

class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding: FragmentMainBinding by viewBinding(FragmentMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        flowTextChanged()
        radioGroupChanged()
    }

    private fun flowTextChanged() {
        viewLifecycleOwner.lifecycleScope.launch {
            binding.searchRequest.textChangedFlow().collect {
                Timber.d(it)
            }
        }
    }

    private fun radioGroupChanged() {
        val flow = callbackFlow<Int> {
            val radioGroupChangedListener = object : RadioGroup.OnCheckedChangeListener {
                override fun onCheckedChanged(radioGroup: RadioGroup, viewId: Int) {
                    trySendBlocking(viewId)
                }
            }
            binding.radioGroup.setOnCheckedChangeListener(radioGroupChangedListener)
/*            awaitClose {
                Timber.d("awaitClose radioGroup")
            }*/
        }

        viewLifecycleOwner.lifecycleScope.launch {
            flow
                .map {
                    when (it) {
                        R.id.radioMovies -> MovieType.MOVIE
                        R.id.radioSeries -> MovieType.SERIES
                        else -> MovieType.EPISODE
                    }
                }
                .collect {
                    Timber.d(it.toString())
                }
        }
    }
}