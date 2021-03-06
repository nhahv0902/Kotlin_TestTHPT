package com.example.nhahv.testthpt

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

@SuppressLint("Registered")
open
/**
 * Created by nhahv on 11/7/17.
 */
class BaseActivity : AppCompatActivity() {

    private var mProgress: ProgressDialog? = null

    inline fun <reified T : AppCompatActivity> switchActivity() {
        startActivity(Intent(this, T::class.java))
    }

    inline fun <reified T : AppCompatActivity> switchActivity(bundle: Bundle) {
        startActivity(Intent(this, T::class.java).putExtras(bundle))
    }

    fun setupToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayShowHomeEnabled(true)
        }
    }

    fun showProgress(message: String) {
        if (mProgress == null){
            mProgress = ProgressDialog(this)
            mProgress!!.setMessage(message)
        }

        mProgress.let {
            if (!it!!.isShowing) it.show()
        }
    }

    fun hideProgress() {
        mProgress.let {
            if (it!!.isShowing) it.dismiss()
        }
    }
}