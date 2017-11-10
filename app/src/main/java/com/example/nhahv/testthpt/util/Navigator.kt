package com.example.nhahv.testthpt.util

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast

/**
 * Created by nhahv on 07/11/2017.
 */
class Navigator(val context: AppCompatActivity) {

    fun toast(message: String, time: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, time).show()
    }

    fun log(message: String) {
        Log.d("TAG", message)
    }

    inline fun <reified T : AppCompatActivity> switchActivity() {
        context.startActivity(Intent(context, T::class.java))
    }

    inline fun <reified T : AppCompatActivity> switchActivity(bundle: Bundle) {
        context.startActivity(Intent(context, T::class.java).putExtras(bundle))
    }

    fun finish() {
        context.finish()
    }

}