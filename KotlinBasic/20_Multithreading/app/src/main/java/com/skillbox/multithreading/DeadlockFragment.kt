package com.skillbox.multithreading

import android.util.Log
import androidx.fragment.app.Fragment

class DeadlockFragment: Fragment() {

    override fun onResume() {
        super.onResume()

/*        val friend1 = Person("Вася")
        val friend2 = Person("Петя")

        val thread1 = Thread {
            friend1.throwBallTo(friend2)
        }

        val thread2 = Thread {
            friend2.throwBallTo(friend1)
        }

        thread1.start()
        thread2.start()*/

        val count1 = CounterObject("Счётчик 1", 0)
        val count2 = CounterObject("Счётчик 2", 0)

        val thread3 = Thread {
            count1.incrementObject(count2)
        }
        val thread4 = Thread {
            count2.incrementObject(count1)
        }
        thread3.start()
        thread4.start()
    }

    class CounterObject(val counterName: String, var counter: Long) {

        fun incrementObject(counter: CounterObject) {
            synchronized(this) {
                Log.d("Deadlock", "$counterName increment: ${counter.counter}")
                Thread.sleep(100)
            }
            counter.counter++
            counter.incrementObject(this)
        }
    }

    data class Person(
        val name: String
    ) {

        fun throwBallTo(friend: Person) {
            synchronized(this) {
                Log.d(
                    "Person",
                    "$name бросает мяч ${friend.name} на потоке ${Thread.currentThread().name}"
                )
                Thread.sleep(500)
            }
            friend.throwBallTo(this)
        }

    }
}