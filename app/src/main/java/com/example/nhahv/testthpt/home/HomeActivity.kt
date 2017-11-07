package com.example.nhahv.testthpt.home

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.example.nhahv.testthpt.BaseActivity
import com.example.nhahv.testthpt.R
import com.example.nhahv.testthpt.databinding.ActivityHomeBinding
import com.example.nhahv.testthpt.util.Navigator
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity() {

    lateinit var viewModel: HomeViewModel
    var drawerFragment: NavigationDrawerFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = HomeViewModel(Navigator(this))

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
        startQuestion.setOnClickListener { }
        nextQuesttion.setOnClickListener {
            if (viewModel.currentQuestion.get() < viewModel.items.size - 1) {
                viewModel.currentQuestion.set(viewModel.currentQuestion.get() + 1)
            }
        }
        previousQuestion.setOnClickListener {
            if (viewModel.currentQuestion.get() > 0) {
                viewModel.currentQuestion.set(viewModel.currentQuestion.get() - 1)
            }
        }
    }
}
