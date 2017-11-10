package com.example.nhahv.testthpt.data

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by nhahv on 11/7/17.
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class User(@SerializedName("UserID") val userId: Long = 2,
                @SerializedName("UserName") val userName: String,
                @SerializedName("RealName") val realName: String = userName,
                @SerializedName("Password") val password: String,
                @SerializedName("GrpID") val grpID: Long = 0,
                @SerializedName("MaKT") val maKT: Long = 0,
                @SerializedName("Remark") val remark: String = ""
) : Parcelable