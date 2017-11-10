package com.example.nhahv.testthpt.home

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import com.example.nhahv.testthpt.BaseActivity
import com.example.nhahv.testthpt.R
import com.example.nhahv.testthpt.databinding.ActivityHomeBinding
import com.example.nhahv.testthpt.util.Navigator
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.toast
import java.time.LocalDateTime
import java.util.*
import kotlin.concurrent.timerTask
import kotlin.system.measureTimeMillis

class HomeActivity : BaseActivity() {

    lateinit var viewModel: HomeViewModel
    var drawerFragment: NavigationDrawerFragment? = null
    private var isRuning = true

    override fun onCreate(savedInstanceState: Bundle?) {


        val bundle = intent.extras
        var maDT: Long = 0
        bundle?.let {
            maDT = it.getLong("maDT")
        }
        viewModel = HomeViewModel(Navigator(this), maDT)

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
        startQuestion.setOnClickListener { isRuning = false }
        nextQuesttion.setOnClickListener {
            if (viewModel.currentQuestion.get() < viewModel.items.size - 1) {
                viewModel.currentQuestion.set(viewModel.currentQuestion.get() + 1)
            }
            viewModel.checkQuestionMade()
        }
        previousQuestion.setOnClickListener {
            if (viewModel.currentQuestion.get() > 0) {
                viewModel.currentQuestion.set(viewModel.currentQuestion.get() - 1)
            }
            viewModel.checkQuestionMade()
        }

        groupAnswer.setOnCheckedChangeListener { group, _ ->
            when (group.checkedRadioButtonId) {
                R.id.checkA -> {
                    toast("Check A")
                }
                R.id.checkB -> {
                    toast("Check B")
                }
                R.id.checkC -> {
                    toast("Check C")
                }
                R.id.checkD -> {
                    toast("Check D")
                }
            }
        }
        /* val timer = Timer()
         timer.schedule(timerTask {
             if (isRuning) {
                 Log.d("TAG", Calendar.getInstance().timeInMillis.toString())
             } else {
                 timer.cancel()
                 timer.purge()
             }
         }, 0, 3000)*/
    }


}
