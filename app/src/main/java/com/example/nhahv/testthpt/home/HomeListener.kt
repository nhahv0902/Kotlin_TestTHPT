package com.example.nhahv.testthpt.home

import com.example.nhahv.testthpt.data.AnswerQuestion

/**
 * Created by nhahv on 21/11/2017.
 */
interface HomeListener {
    fun setAnswer(answer: AnswerQuestion?)
    fun closeDrawer()
}