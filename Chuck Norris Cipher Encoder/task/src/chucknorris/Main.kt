package chucknorris

fun main() {
    println("Input string:")
    val input: String = readln()
    for (position in input.indices) {
        if (position != 0) {
            print(" ")
        }
        print("${input[position]}")
    }
}