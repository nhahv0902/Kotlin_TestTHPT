package com.example.nhahv.testthpt.viewtest

import android.databinding.ObservableField
import com.example.nhahv.testthpt.BaseRecyclerAdapter
import com.example.nhahv.testthpt.BaseViewModel
import com.example.nhahv.testthpt.R
import com.example.nhahv.testthpt.data.Question
import com.example.nhahv.testthpt.util.Navigator

/**
 * Created by hoang.van.nha on 12/12/2017.
 */
class ViewTestViewModel(private val navigator: Navigator, val questions: ArrayList<Question>) : BaseViewModel(), BaseRecyclerAdapter.OnClickItem<Question> {
    override fun onClickItem(item: Question, position: Int) {

    }

    val adapter: ObservableField<BaseRecyclerAdapter<Question>> =
            ObservableField(BaseRecyclerAdapter<Question>(questions, R.layout.item_view_test, this))
}