package com.example.nhahv.testthpt

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by nhahv on 11/7/17.
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class User(val userName: String, val password: String) : Parcelable