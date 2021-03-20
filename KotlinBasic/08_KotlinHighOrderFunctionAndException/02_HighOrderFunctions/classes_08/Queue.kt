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

    fun filter(callbackFilter: () -> Unit): List<T> {
//        items.slice(0..list.lastIndex) почему то не работают обычные функции
        return items.filter { it.toString().contains("a") } /*
 метод возвращает очередь
 безотносительно принимаемой функции и работает только со строками.
 */
    }
}