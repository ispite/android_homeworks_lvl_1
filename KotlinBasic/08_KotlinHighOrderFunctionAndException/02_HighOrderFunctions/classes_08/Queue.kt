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

/*    fun filter(callbackFilter: (List<T>) -> List<T>):List<T>{
        return callbackFilter(items)
    }*/

    fun filter(callbackFilter: () -> Unit):List<T>{
        return items.filter { it == 'a' }
    }
}