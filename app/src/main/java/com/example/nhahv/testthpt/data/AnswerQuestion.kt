package com.example.nhahv.testthpt.data

/**
 * Created by nhahv on 07/11/2017.
 */
enum class AnswerQuestion(val value: Int) {
    A(1), B(2), C(3), D(4), NOT(0);

    fun getValue(): String {
        return when (value) {
            1 -> "A"
            2 -> "B"
            3 -> "C"
            4 -> "D"
            else -> ""
        }
    }

    fun getValueChar(): Char =
            when (value) {
                1 -> 'A'
                2 -> 'B'
                3 -> 'C'
                4 -> 'D'
                else -> '0'
            }

    fun isAnswer() = value != 0
}