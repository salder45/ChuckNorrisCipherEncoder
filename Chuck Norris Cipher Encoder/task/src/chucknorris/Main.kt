package chucknorris

fun main() {
    var runAgain: Boolean = true
    do {
        println("Please input operation (encode/decode/exit):")
        val input: String = readln()
        when (input) {
            "encode" -> encode()
            "decode" -> decode()
            "exit" -> runAgain = false
            else -> println("There is no '$input' operation")
        }
    } while(runAgain)
    println("Bye!")
}

fun encode() {
    println("Input string:")
    val input: String = readln()
    println("Encoded string:")
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

fun decode() {
    println("Input encoded string:")
    val input: String = readln()
    var notValid: Boolean = false
    for (chr in input) {
        if(chr != ' ' && chr != '0') {
            notValid = true
        }
    }
    val encodedList: List<String> = input.split(" ").toMutableList()
    if (encodedList.size % 2 != 0) {
        notValid = true
    }
    var binaryResult: String = ""
    for (index in 0 until encodedList.size / 2) {
        val binaryBlock: String = encodedList[index * 2]
        if (binaryBlock != "0" && binaryBlock != "00") {
            notValid = true
            break
        }
        val digitsBlock: String = encodedList[index* 2 + 1]
        val binaryValue = if (binaryBlock == "0")  {
            "1"
        } else {
            "0"
        }
        binaryResult+= binaryValue.repeat(digitsBlock.length)
    }
    if (binaryResult.length % 7 != 0) {
        notValid = true
    }

    if(!notValid) {
        println("Decoded string:")
        var decodedValue: String = ""
        for (iteration in 0 until binaryResult.length / 7) {
            val startingIndex: Int = iteration * 7
            val binaryValue: String = binaryResult.substring(startingIndex,startingIndex + 7)
            val decimalValue:  Int =  Integer.parseInt(binaryValue, 2)
            decodedValue+= decimalValue.toChar()
        }
        println(decodedValue)
    } else {
        println("Encoded string is not valid.")
    }
}