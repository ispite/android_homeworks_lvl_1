package classes_08

class Queue<T> {
//    var items = listOf<T>()
    var items = mutableListOf<T>()

    fun enqueue(item: T){
        items.add(item)
    }

    fun dequeue(): T?{
        if (items.size > 0) {
            return items.removeFirst()
        } else return null
    }
}