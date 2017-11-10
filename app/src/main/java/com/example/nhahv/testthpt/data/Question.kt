package com.example.nhahv.testthpt.data

import com.google.gson.annotations.SerializedName

/**
 * Created by nhahv on 07/11/2017.
 * <>
 *
 */
data class Question(@SerializedName("MaCD") var maCH: Long = 0,
                    @SerializedName("NoiDung") val content: String,
                    @SerializedName("LoCHN") var loCHN: Int = 0,
                    @SerializedName("MaCHN") var maCHN: Long = 0,
                    @SerializedName("STT") var stt: Int = 0,
                    @SerializedName("NoiDungCauHoiNho") var contentSmall: String = "",
                    @SerializedName("MaPA") var maPA: Long = 0,
                    @SerializedName("KyHieu") var kyHieu: String = "",
                    @SerializedName("Diem") var diem: Float = 0f,
                    @SerializedName("LoiDan") var loiDan: String = "",
                    @SerializedName("MaDT") var maDT: Int = 0,
                    @SerializedName("MaChiTietDT") var maChiTietDT: Int = 0,
                    @SerializedName("P1") val answerA: String,
                    @SerializedName("P2") val answerB: String,
                    @SerializedName("P3") val answerC: String,
                    @SerializedName("P4") val answerD: String,
                    var answer: AnswerQuestion? = null
)