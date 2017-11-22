package com.example.nhahv.testthpt.questions

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.nhahv.testthpt.BaseActivity
import com.example.nhahv.testthpt.R
import com.example.nhahv.testthpt.databinding.ActivityQuestionsBinding
import com.example.nhahv.testthpt.login.LoginActivity
import com.example.nhahv.testthpt.util.Navigator
import kotlinx.android.synthetic.main.activity_questions.*

class QuestionsActivity : BaseActivity() {

    private lateinit var viewModel: QuestionsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityQuestionsBinding = DataBindingUtil.setContentView(this, R.layout.activity_questions)

        viewModel = QuestionsViewModel(Navigator(this))
        binding.viewModel = viewModel

        title = "Danh sách đề thi"
        supportActionBar?.setDisplayShowHomeEnabled(true)

        event()
    }

    fun event() {
        reload.setOnClickListener {
            viewModel.getQuestions()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_questions, menu)
        return super.onCreateOptionsMenu(menu)
    }



    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        if (item?.itemId == R.id.logout){
            switchActivity<LoginActivity>()
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getQuestions()
    }
}
