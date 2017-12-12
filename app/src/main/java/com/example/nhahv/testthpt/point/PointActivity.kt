package com.example.nhahv.testthpt.point

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.nhahv.testthpt.BaseActivity
import com.example.nhahv.testthpt.R
import com.example.nhahv.testthpt.data.InfoQuestion
import com.example.nhahv.testthpt.data.Question
import com.example.nhahv.testthpt.databinding.ActivityPointBinding
import com.example.nhahv.testthpt.viewtest.ViewTestActivity
import com.google.gson.Gson
import java.util.ArrayList
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_point.*


class PointActivity : BaseActivity() {

    private lateinit var mViewModel: PointViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        val bundle = intent.extras
        var question: InfoQuestion = InfoQuestion()
        var point: Float = 0f
        var questions: ArrayList<Question> = ArrayList()
        bundle?.let {
            question = Gson().fromJson(it.getString("info"), InfoQuestion::class.java)
            point = it.getFloat("point")

            questions = Gson().fromJson(it.getString("questions"), object : TypeToken<List<Question>>() {}.type)
        }
        mViewModel = PointViewModel(question, point)
        super.onCreate(savedInstanceState)
        val binding: ActivityPointBinding = DataBindingUtil.setContentView(this, R.layout.activity_point)
        binding.viewModel = mViewModel
        title = "Điểm môn ${question.subjectTitle}"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        viewTest.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("questions", intent.extras.getString("questions"))
            switchActivity<ViewTestActivity>(bundle)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
