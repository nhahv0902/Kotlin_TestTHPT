package com.example.nhahv.testthpt.point

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.nhahv.testthpt.R
import com.example.nhahv.testthpt.data.InfoQuestion
import com.example.nhahv.testthpt.databinding.ActivityPointBinding
import com.google.gson.Gson

class PointActivity : AppCompatActivity() {

    private lateinit var mViewModel: PointViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        val bundle = intent.extras
        var question: InfoQuestion = InfoQuestion()
        var point: Float = 0f
        bundle?.let {
            question = Gson().fromJson(it.getString("info"), InfoQuestion::class.java)
            point = it.getFloat("point")
        }
        mViewModel = PointViewModel(question, point)
        super.onCreate(savedInstanceState)
        val binding: ActivityPointBinding = DataBindingUtil.setContentView(this, R.layout.activity_point)
        binding.viewModel = mViewModel
        title = "Diem mon ${question.subjectTitle}"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
