
fun main(){
    val firstName = "Denis"
    val lastName = "Solovyov"
    var height = 187
    val weight = 33.5f
    var isChild = height < 150 && weight <40
    var info = "My name is ${firstName+' '+lastName}. My height is $height cm. My weigh is $weight kg. I'm ${if (isChild) "a child" else "an adult"}"
    println(info)
    height = 139
    isChild = height < 150 && weight <40
    info = "My name is ${firstName+' '+lastName}. My height is $height cm. My weigh is $weight kg. I'm ${if (isChild) "a child" else "an adult"}"
    println(info)
}