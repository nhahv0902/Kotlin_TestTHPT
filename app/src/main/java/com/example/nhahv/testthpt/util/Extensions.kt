package com.example.nhahv.testthpt.util

import android.content.Context
import android.widget.Toast
import android.widget.Toast.makeText

/**
 * Created by nhahv on 07/11/2017.
 */

fun Toast.toast(context: Context, message: String, time: Int = Toast.LENGTH_SHORT) {
    makeText(context, message, time).show()
}