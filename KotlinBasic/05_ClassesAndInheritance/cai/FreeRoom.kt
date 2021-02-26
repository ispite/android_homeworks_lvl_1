package cai

class FreeRoom(
        area: Double,
        override var title: String = "Свободная комната")
    : Room(area) {
}