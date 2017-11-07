package com.example.nhahv.testthpt

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity

@SuppressLint("Registered")
open
/**
 * Created by nhahv on 11/7/17.
 */
class BaseActivity : AppCompatActivity() {

    inline fun <reified T : AppCompatActivity> switchActivity() {
        startActivity(Intent(this, T::class.java))
    }
}