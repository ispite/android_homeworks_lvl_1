package cai

open class Room(val area: Double) {
    protected open val title: String = "Обычная комната"

    fun getDescription() {
        println("Это $title, её площадь = $area")
    }
}