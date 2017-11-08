package com.example.nhahv.testthpt.home

import android.databinding.ObservableField
import android.databinding.ObservableInt
import com.example.nhahv.testthpt.BaseRecyclerAdapter
import com.example.nhahv.testthpt.BaseViewModel
import com.example.nhahv.testthpt.R
import com.example.nhahv.testthpt.data.AnswerQuestion
import com.example.nhahv.testthpt.data.Question
import com.example.nhahv.testthpt.util.Navigator

/**
 * Created by nhahv on 11/7/17.
 */
class HomeViewModel(private val navigator: Navigator) : BaseViewModel(), BaseRecyclerAdapter.OnClickItem<Question> {

    val items = ArrayList<Question>()
    val adapter = ObservableField<BaseRecyclerAdapter<Question>>(BaseRecyclerAdapter<Question>(items, R.layout.nav_drawer_row, this))
    val currentQuestion = ObservableInt(0)
    val numberMade = ObservableInt(0)

    init {
        items.add(Question("Cau 1: chuyen tinh mua dong", "A", "B", "C", "D", AnswerQuestion.A))
        items.add(Question("Cau 1: chuyen tinh mua dong", "A", "B", "C", "D", AnswerQuestion.D))
        items.add(Question("Cau 1: chuyen tinh mua dong", "A", "B", "C", "D", AnswerQuestion.C))
        items.add(Question("Cau 1: chuyen tinh mua dong", "A", "B", "C", "D", AnswerQuestion.D))
        items.add(Question("Cau 1: chuyen tinh mua dong", "A", "B", "C", "D", AnswerQuestion.A))
    }

    override fun onClickItem(item: Question, position: Int) {
        navigator.toast("$position")
    }

    fun checkQuestionMade() {
        numberMade.set(items.count { it.answer != AnswerQuestion.NOT })
    }
}