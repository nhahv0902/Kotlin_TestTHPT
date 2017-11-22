package com.example.nhahv.testthpt.point

import com.example.nhahv.testthpt.BaseViewModel
import com.example.nhahv.testthpt.data.InfoQuestion

/**
 * Created by nhahv on 22/11/2017.
 */
class PointViewModel(private val question: InfoQuestion, private val point: Float) : BaseViewModel() {

    val hoten = question.name
    val birthday = question.birthDay.substring(0, question.birthDay.indexOf("T"))
    val kyThi = question.nameTest
    val monThi = question.subjectTitle

    val diem = point.toString()

}