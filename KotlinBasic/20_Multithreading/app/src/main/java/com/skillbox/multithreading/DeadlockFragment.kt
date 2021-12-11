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

        val count1 = Counter("Счётчик 1")
        val count2 = Counter("Счётчик 2")

        val thread3 = Thread {
            count1.incrementObject(count2)
        }
        val thread4 = Thread {
            count1.incrementObject(count1)
        }
        thread3.start()
        thread4.start()
    }

    data class Counter(val counterName: String) {

        fun incrementObject(counter: Counter) {
            Log.d("Deadlock", "incrementObject: $counter")
            Thread.sleep(100)
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