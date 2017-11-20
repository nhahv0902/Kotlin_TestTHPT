package com.example.nhahv.testthpt.util

import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
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

    fun string(@StringRes stringID: Int) = context.getString(stringID)

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

    fun showDialogConfirm(endTest: () -> Unit) {
        AlertDialog.Builder(context)
                .setMessage("Hãy chắc chắn rằng bạn đã làm xong, nộp bài để kết thúc bài thi!")
                .setPositiveButton("Nộp bài", { dialog, i ->
                    dialog.dismiss()
                    endTest()
                })
                .setNegativeButton("Cancel", { dialog, i -> dialog.dismiss() })
                .show()
    }

    fun showDialogCount(endUI: () -> Unit, count: String) {
        AlertDialog.Builder(context)
                .setMessage(count)
                .setPositiveButton("Ok", { dialog, _ ->
                    dialog.dismiss()
                    endUI()
                })
                .show()
    }
}