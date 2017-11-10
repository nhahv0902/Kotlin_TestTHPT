package com.example.nhahv.testthpt.questions

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.nhahv.testthpt.R
import com.example.nhahv.testthpt.databinding.ActivityQuestionsBinding
import com.example.nhahv.testthpt.util.Navigator

class QuestionsActivity : AppCompatActivity() {

    private lateinit var viewModel: QuestionsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityQuestionsBinding = DataBindingUtil.setContentView(this, R.layout.activity_questions)

        viewModel = QuestionsViewModel(Navigator(this))
        binding.viewModel = viewModel
    }
}
