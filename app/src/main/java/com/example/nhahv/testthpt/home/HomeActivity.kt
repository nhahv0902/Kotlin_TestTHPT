package com.example.nhahv.testthpt.home

import android.app.AlertDialog
import android.app.ProgressDialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import com.example.nhahv.testthpt.BaseActivity
import com.example.nhahv.testthpt.R
import com.example.nhahv.testthpt.data.AnswerQuestion
import com.example.nhahv.testthpt.data.InfoQuestion
import com.example.nhahv.testthpt.data.Question
import com.example.nhahv.testthpt.databinding.ActivityHomeBinding
import com.example.nhahv.testthpt.util.Navigator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*
import kotlin.concurrent.timerTask

class HomeActivity : BaseActivity(), HomeListener {

    lateinit var viewModel: HomeViewModel
    var drawerFragment: NavigationDrawerFragment? = null
    private var mProgress: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {


        val bundle = intent.extras
        var questions: ArrayList<Question> = ArrayList()
        var question: InfoQuestion = InfoQuestion()
        bundle?.let {
            question = Gson().fromJson(bundle.getString("info"), InfoQuestion::class.java)
            questions = Gson().fromJson(bundle.getString("question"), object : TypeToken<List<Question>>() {}.type)
            title = bundle.getString("title")
        }
        viewModel = HomeViewModel(Navigator(this), this, questions, question)

        super.onCreate(savedInstanceState)
        val binding: ActivityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        binding.viewModel = viewModel
        setup()
        event()
    }

    private fun setup() {
        setupToolbar(toolbar)
        drawerFragment = supportFragmentManager.findFragmentById(R.id.navigation_drawer) as NavigationDrawerFragment?
        drawerFragment?.setUp(R.id.navigation_drawer, drawerLayout, toolbar)
    }

    private fun event() {
        startQuestion.setOnClickListener {
            if (viewModel.running.get()) {
                viewModel.endTest()
            } else {
                viewModel.startTest()
            }
        }
        nextQuesttion.setOnClickListener {
            if (!viewModel.running.get()) {
                return@setOnClickListener
            }
            viewModel.next = true
            if (viewModel.currentQuestion.get() < viewModel.questions.size - 1) {
                viewModel.currentQuestion.set(viewModel.currentQuestion.get() + 1)
            }
            viewModel.checkQuestionMade()
        }
        previousQuestion.setOnClickListener {
            if (!viewModel.running.get()) {
                return@setOnClickListener
            }
            if (viewModel.currentQuestion.get() > 0) {
                viewModel.currentQuestion.set(viewModel.currentQuestion.get() - 1)
            }
            viewModel.checkQuestionMade()
        }

        groupAnswer.setOnCheckedChangeListener { group, _ ->
            if (viewModel.next || viewModel.previous) return@setOnCheckedChangeListener
            when (group.checkedRadioButtonId) {
                R.id.checkA -> {
                    if (checkA.isChecked) viewModel.updateAnswer(AnswerQuestion.A)
                }
                R.id.checkB -> {
                    if (checkB.isChecked) viewModel.updateAnswer(AnswerQuestion.B)
                }
                R.id.checkC -> {
                    if (checkC.isChecked) viewModel.updateAnswer(AnswerQuestion.C)
                }
                R.id.checkD -> {
                    if (checkD.isChecked) viewModel.updateAnswer(AnswerQuestion.D)
                }
            }
        }
        val timer = Timer()
        timer.schedule(timerTask {
            if (viewModel.running.get()) {
                viewModel.updateAnswer()
                Log.d("TAG", Calendar.getInstance().timeInMillis.toString())
            } else {
                timer.cancel()
                timer.purge()
            }
        }, 0, 5 * 60 * 1000)
    }


//    private fun clearAnswer() {
//        groupAnswer.clearCheck()
//    }

    override fun setAnswer(answer: AnswerQuestion?) {
        groupAnswer.clearCheck()
        answer?.let {
            when (it) {
                AnswerQuestion.A -> {
                    checkA.isChecked = true
                    viewModel.next = false
                    viewModel.previous = false
                }
                AnswerQuestion.B -> {
                    checkB.isChecked = true
                    viewModel.next = false
                    viewModel.previous = false
                }
                AnswerQuestion.C -> {
                    checkC.isChecked = true
                    viewModel.next = false
                    viewModel.previous = false
                }
                AnswerQuestion.D -> {
                    checkC.isChecked = true
                    viewModel.next = false
                    viewModel.previous = false
                }
                AnswerQuestion.NOT -> {
                    checkA.isChecked = false
                    checkB.isChecked = false
                    checkC.isChecked = false
                    checkD.isChecked = false
                    viewModel.next = false
                    viewModel.previous = false
                }
            }
        }
    }

    override fun closeDrawer() {
        drawerLayout.closeDrawers()
    }


    override fun onStop() {
        super.onStop()
        if (viewModel.running.get()) {
            viewModel.postAnswer()
            viewModel.running.set(false)
            finish()
        }
    }

    override fun onBackPressed() {
        if (viewModel.running.get()) {
            AlertDialog.Builder(this)
                    .setMessage("Bạn có muốn thoát khỏi bài thi không?")
                    .setPositiveButton("Thoát", { dialog, _ ->
                        viewModel.postAnswer()
                        viewModel.running.set(false)
                        dialog.dismiss()
                        super.onBackPressed()
                    })
                    .setNegativeButton("Không", { dialog, _ -> dialog.dismiss() })
                    .show()
        } else {
            super.onBackPressed()
        }
    }

    override fun showProgressDialog() {
        if (mProgress == null){
            mProgress = ProgressDialog(this)
            mProgress!!.setMessage("Đang nộp bài.")
        }

        mProgress.let {
            if (!it!!.isShowing) it.show()
        }
    }

    override fun hideProgressDialog() {
        mProgress.let {
            if (it!!.isShowing) it.dismiss()
        }
    }
}
