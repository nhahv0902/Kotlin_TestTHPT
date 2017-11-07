package com.example.nhahv.testthpt.register

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.example.nhahv.testthpt.BaseActivity
import com.example.nhahv.testthpt.R
import com.example.nhahv.testthpt.databinding.ActivityRegisterBinding
import com.example.nhahv.testthpt.login.LoginActivity
import com.example.nhahv.testthpt.util.Navigator
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() {

    private lateinit var mViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_register)

        mViewModel = RegisterViewModel(Navigator(this))
        binding.viewModel = mViewModel

        event()
    }

    private fun event() {
        login.setOnClickListener { switchActivity<LoginActivity>() }
        register.setOnClickListener {
            mViewModel.register(userName.text.toString(), password.text.toString(), repeatPassword.text.toString())
        }
    }

}
