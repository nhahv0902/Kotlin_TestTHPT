package com.example.nhahv.testthpt.viewtest

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.nhahv.testthpt.R
import com.example.nhahv.testthpt.data.Question
import com.example.nhahv.testthpt.databinding.ActivityViewTestBinding
import com.example.nhahv.testthpt.util.Navigator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ViewTestActivity : AppCompatActivity() {

    private lateinit var mViewModel: ViewTestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val temp = intent.extras.getString("questions")
        val questions: ArrayList<Question> = Gson().fromJson(temp, object : TypeToken<List<Question>>() {}.type)
        val binding: ActivityViewTestBinding = DataBindingUtil.setContentView(this, R.layout.activity_view_test)

        mViewModel = ViewTestViewModel(Navigator(this), questions)
        binding.viewModel = mViewModel


    }
}
