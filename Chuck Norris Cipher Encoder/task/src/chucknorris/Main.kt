package chucknorris

fun main() {
    println("Input string:")
    val input: String = readln()
    println("The result:")
    var result: String = ""
    var binary: String = ""
    for (inputIndex in input.indices) {
        var chr: Char = input[inputIndex]
        binary+= "%07d".format(Integer.toBinaryString(chr.code).toInt())
    }
    var previousChar: Char = ' '
    for (binaryIndex in binary.indices) {
        val currentChar: Char = binary[binaryIndex]
        result+= when (currentChar) {
            previousChar -> "0"
            else ->  {
                previousChar = currentChar
                var firstSequence: String = if (binaryIndex > 0) {
                        " "
                    } else {
                        ""
                    }
                firstSequence+= when (currentChar){
                    '1' -> "0 0"
                    else -> "00 0"
                }
                firstSequence
            }
        }
    }
    println(result)
}