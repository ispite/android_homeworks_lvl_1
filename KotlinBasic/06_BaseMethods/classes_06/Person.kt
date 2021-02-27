package classes_06

class Person(
        val height: Int,
        val weight: Int,
        val name: String
) {
    val pets = mutableSetOf<Animal>()
    fun buyPet() {
        pets.add(Animal((0..100).random(), (0..100).random(), getRandomString((3..12).random())))
    }

    private fun getRandomString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z')
        return (1..length)
                .map { allowedChars.random() }
                .joinToString("")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Person

        if (height != other.height) return false
        if (weight != other.weight) return false
        if (name != other.name) return false
        if (pets != other.pets) return false

        return true
    }

    override fun hashCode(): Int {
        var result = height
        result = 31 * result + weight
        result = 31 * result + name.hashCode()
        result = 31 * result + pets.hashCode()
        return result
    }

    override fun toString(): String {
        return "Person(height=$height, weight=$weight, name='$name', pets=$pets)"
    }

}