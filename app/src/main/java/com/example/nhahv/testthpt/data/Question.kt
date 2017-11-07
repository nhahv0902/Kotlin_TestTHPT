package com.example.nhahv.testthpt.data

/**
 * Created by nhahv on 07/11/2017.
 */
data class Question(val content: String,
                    val answerA: String,
                    val answerB: String,
                    val answerC: String,
                    val answerD: String,
                    var answer: AnswerQuestion?
)