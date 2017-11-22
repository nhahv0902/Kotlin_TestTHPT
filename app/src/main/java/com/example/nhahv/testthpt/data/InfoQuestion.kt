package com.example.nhahv.testthpt.data

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by nhahv on 10/11/2017.
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class InfoQuestion(
        @SerializedName("MaTS") var maTS: Long = 0,
        @SerializedName("SBD") var sbd: String = "",
        @SerializedName("HoTen") var name: String = "",
        @SerializedName("NgaySinh") var birthDay: String = "",
        @SerializedName("SoPhong") var numberRoom: Long = 0,
        @SerializedName("MaGoc") var maGoc: Long = 0,
        @SerializedName("TenNguon") var nameSource: String = "",
        @SerializedName("MaTSDT") var maTSDT: Long = 0,
        @SerializedName("MaDT") var maDT: Long = 0,
        @SerializedName("TenMH") var subjectTitle: String = "",
        @SerializedName("TenBaiThi") var nameTest: String = "",
        @SerializedName("TinhTrangThi") var status: Boolean = false,
        @SerializedName("ThoiGian") var time: Int = 0
) : Parcelable