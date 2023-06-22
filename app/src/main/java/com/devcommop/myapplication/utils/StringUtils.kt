package com.devcommop.myapplication.utils

class StringUtils {
    companion object {
        fun increaseLastCharByOne(input: String): String {
            if (input.isEmpty()) {
                return input
            }

            val lastChar = input.last()
            val incrementedChar = (lastChar.toInt() + 1).toChar()

            return input.dropLast(1) + incrementedChar
        }
    }
}