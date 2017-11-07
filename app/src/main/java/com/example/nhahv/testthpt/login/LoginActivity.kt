package com.example.nhahv.testthpt.login

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.example.nhahv.testthpt.BaseActivity
import com.example.nhahv.testthpt.R
import com.example.nhahv.testthpt.databinding.ActivityLoginBinding
import com.example.nhahv.testthpt.register.RegisterActivity
import com.example.nhahv.testthpt.util.Navigator
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    private lateinit var mViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = LoginViewModel(Navigator(this))

        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = mViewModel

        event()
    }

    private fun event() {
        login.setOnClickListener {
            mViewModel.login(userName.text.toString(), password.text.toString())
        }
        register.setOnClickListener { switchActivity<RegisterActivity>() }
    }
}
