import cai.*

fun main() {
    val r = Room(11.0)
    r.getDescription()
    val bath = Bathroom(5.0)
    bath.getDescription()
    val bed = Bedroom(15.0)
    bed.getDescription()
    val k = Kitchen(12.0)
    k.getDescription()
    val l = LivingRoom(23.0)
    l.getDescription()
    val c = ChildrensRoom(8.0)
    c.getDescription()
    var f = FreeRoom(8.0)
    f.getDescription()
    f = FreeRoom(16.0, "Мастерская")
    f.getDescription()
}