package com.example.nhahv.testthpt.util

import android.databinding.BindingAdapter
import android.webkit.WebView

/**
 * Created by nhahv on 11/20/17.
 */

@BindingAdapter("data")
fun dataWebview(view: WebView, data: String?) {
    data?.let {
        view.settings.defaultTextEncodingName = "utf-8"
        view.loadData( it, "text/html; charset=utf-8","UTF-8")
//        view.loadUrl("file://android_asset/text.html")
    }
}