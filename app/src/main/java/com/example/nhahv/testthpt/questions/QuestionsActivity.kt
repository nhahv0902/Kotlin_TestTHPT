package com.example.nhahv.testthpt.questions

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.MenuItem
import com.example.nhahv.testthpt.BaseActivity
import com.example.nhahv.testthpt.R
import com.example.nhahv.testthpt.databinding.ActivityQuestionsBinding
import com.example.nhahv.testthpt.util.Navigator

class QuestionsActivity : BaseActivity() {

    private lateinit var viewModel: QuestionsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityQuestionsBinding = DataBindingUtil.setContentView(this, R.layout.activity_questions)

        viewModel = QuestionsViewModel(Navigator(this))
        binding.viewModel = viewModel

        title  = "Danh sách đề thi"
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
