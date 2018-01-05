package com.example.nhahv.testthpt.util

import android.databinding.BindingAdapter
import android.text.Html
import android.webkit.WebView
import android.widget.TextView
import com.example.nhahv.testthpt.data.AnswerQuestion

/**
 * Created by nhahv on 11/20/17.
 */

@BindingAdapter("data")
fun dataWebview(view: WebView, data: String?) {
    data?.let {
        view.settings.defaultTextEncodingName = "utf-8"
        view.loadUrl("about:blank")
        view.loadData(it, "text/html; charset=utf-8", "UTF-8")
    }
}


@BindingAdapter("numberQuestion", "answer")
fun bindQuestion(view: TextView, number: Int, answer: AnswerQuestion) {
    view.text = Html.fromHtml("<p>Câu <font color=\"red\">$number</font>: Đáp án <font color=\"red\">${answer.getValue()}</font></p>")
}