package classes_08

class Queue<T> {
    private var items = mutableListOf<T>()

    fun enqueue(item: T) {
        items.add(item)
    }

    fun dequeue(): T? {
        return if (items.size > 0) {
            items.removeFirst()
        } else null
    }

    fun filter(callbackFilter: (T) -> Boolean): List<T> {
        val filteredQueue = mutableListOf<T>()
        for (index in 0 until items.size) {
            val element = items[index]
            println(element)
            if (callbackFilter(element)) filteredQueue.add(element)
        }
        return filteredQueue
    }
}