package com.skillbox.multithreading

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_race_condition.*

class RaceConditionFragment : Fragment(R.layout.fragment_race_condition) {

    private var value: Int = 0

    override fun onResume() {
        super.onResume()
        makeMultithreadingIIncrement()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        computeWithoutSynchronization()
        computeWithSynchronization()
    }

    private fun computeWithoutSynchronization() {
        computeWithoutSynchronization.setOnClickListener {

            if (inputThreadCount.isNotNullOrEmpty("Это поле обязательно для заполнения") &&
                inputIncrement.isNotNullOrEmpty("Это поле обязательно для заполнения")
            ) {
                var variable: Long = 0
                val startTime = System.currentTimeMillis()
                val threadsCount = inputThreadCount.text.toString().toInt()
                //Log.d("TAG", "computeWithThreads: $threadsCount")
                val increment = inputIncrement.text.toString().toInt()
                //Log.d("TAG", "computeWithThreads: $increment")
                (0 until threadsCount).map {
                    Thread {
                        for (i in 0 until increment) {
                            variable += increment
                            //Log.d("TAG", "computeWithThreads: $variable")
                        }
                    }.apply { start() }
                }.map { it.join() }
                val spentTime = System.currentTimeMillis() - startTime
                val expectedValue = threadsCount * increment * increment
                val resultString =
                    "Результат=$variable Ожидаемый результат:$expectedValue Потраченное время:$spentTime"
                resultWithoutSynchronization.text = resultString
            }

        }
    }

    private fun computeWithSynchronization() {
        computeWithSynchronization.setOnClickListener {
            if (inputThreadCount.isNotNullOrEmpty("Это поле обязательно для заполнения") &&
                inputIncrement.isNotNullOrEmpty("Это поле обязательно для заполнения")
            ) {
                var variable: Long = 0
                val startTime = System.currentTimeMillis()
                val threadsCount = inputThreadCount.text.toString().toInt()
                //Log.d("TAG", "computeWithThreads: $threadsCount")
                val increment = inputIncrement.text.toString().toInt()
                //Log.d("TAG", "computeWithThreads: $increment")
                (0 until threadsCount).map {
                    Thread {
                        synchronized(this) {
                            for (i in 0 until increment) {
                                variable += increment
                                //Log.d("TAG", "computeWithThreads: $variable")
                            }
                        }
                    }.apply { start() }
                }.map { it.join() }
                val spentTime = System.currentTimeMillis() - startTime
                val expectedValue = threadsCount * increment * increment
                val resultString =
                    "Результат=$variable Ожидаемый результат:$expectedValue Потраченное время:$spentTime"
                resultWithSynchronization.text = resultString
            }

        }
    }

    private fun makeMultithreadingIIncrement() {
        val threadCount = 100
        val incrementCount = 1000000
        val expectedValue = value + threadCount * incrementCount

        (0 until threadCount).map {
            Thread {
                synchronized(this) {
                    for (i in 0 until incrementCount) {
                        value++
                    }
                }
            }.apply {
                start()
            }
        }
            .map { it.join() }
        Toast.makeText(requireContext(), "value=$value, expected=$expectedValue", Toast.LENGTH_LONG)
            .show()
    }

}