package com.example.nhahv.testthpt.questions

import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import com.example.nhahv.testthpt.BaseRecyclerAdapter
import com.example.nhahv.testthpt.BaseViewModel
import com.example.nhahv.testthpt.R
import com.example.nhahv.testthpt.data.InfoQuestion
import com.example.nhahv.testthpt.data.Question
import com.example.nhahv.testthpt.data.local.SharePrefs
import com.example.nhahv.testthpt.home.HomeActivity
import com.example.nhahv.testthpt.util.Constant
import com.example.nhahv.testthpt.util.Constant.METHOD_GET_QUESTION
import com.example.nhahv.testthpt.util.Constant.NAME_SPACE
import com.example.nhahv.testthpt.util.Constant.SOAP_GET_QUESTION
import com.example.nhahv.testthpt.util.Constant.URL
import com.example.nhahv.testthpt.util.Navigator
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE

/**
 * Created by nhahv on 09/11/2017.
 */
class QuestionsViewModel(private val navigator: Navigator, private val listener: QuestionsListener) : BaseViewModel(), BaseRecyclerAdapter.OnClickItem<InfoQuestion> {

    val items = ArrayList<InfoQuestion>()
    val loading = ObservableInt(View.GONE)

    var adapter = ObservableField<BaseRecyclerAdapter<InfoQuestion>>(BaseRecyclerAdapter<InfoQuestion>(items, R.layout.item_questions, this))


    init {
        getQuestions()
    }

    fun getQuestions() {
        loading.set(View.VISIBLE)
        val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        doAsync {
            val request = SoapObject(NAME_SPACE, METHOD_GET_QUESTION)
            request.addProperty("TenDangNhap", SharePrefs.getInstance(navigator.context).get("user", SharePrefs.TypeSharePrefs.STRING))
            val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
            envelope.dotNet = true
            envelope.setOutputSoapObject(request)

            val http = HttpTransportSE(URL)
            try {
                http.call(SOAP_GET_QUESTION, envelope)

                val response = envelope.response as SoapObject
                loading.set(View.GONE)
                var index = 0
                items.clear()
                while (index < response.propertyCount) {
                    val element = response.getProperty(index) as SoapObject
                    val maDT = element.getProperty("MaDT").toString()
                    val tenMH = element.getProperty("TenMH").toString()
                    val tenBaiThi = element.getProperty("TenBaiThi").toString()
                    val maTSDT = element.getProperty("MaTSDT").toString()
                    val hoTen = element.getProperty("HoTen").toString()
                    val ngaySinh = element.getProperty("NgaySinh").toString()
                    val time = element.getProperty("ThoiGian").toString()
                    val status = element.getProperty("TinhTrangThi").toString()
                    items.add(InfoQuestion(maDT = maDT.toLong(), subjectTitle = tenMH,
                            nameTest = tenBaiThi, maTSDT = maTSDT.toLong(),
                            birthDay = ngaySinh, name = hoTen, time = time.toInt(),
                            status = status.toBoolean()))
                    navigator.log("$items")
                    index++
                }
                adapter.notifyChange()
            } catch (ex: Exception) {
                navigator.toast("Click Reload để tải lại dữ liệu.")
            }

        }
    }

    override fun onClickItem(item: InfoQuestion, position: Int) {
        loadExample(item)
    }

    private fun loadExample(question: InfoQuestion) {
        listener.showProgressDialog()
        val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)


        doAsync {
            val request = SoapObject(NAME_SPACE, Constant.METHOD_INFO_TEST)
            request.addProperty("maThiSinhDeThi", question.maTSDT)
            val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
            envelope.dotNet = true
            envelope.setOutputSoapObject(request)

            val http = HttpTransportSE(URL)

            try {
                http.call(Constant.SOAP_INFO_TEST, envelope)
                val response = envelope.response as SoapObject

                val items: ArrayList<Question> = ArrayList()

                var index = 0
                while (index < response.propertyCount) {
                    val element = response.getProperty(index) as SoapObject
                    val content = element.getProperty("NoiDung").toString()
                    val p1 = element.getProperty("P1").toString()
                    val p2 = element.getProperty("P2").toString()
                    val p3 = element.getProperty("P3").toString()
                    val p4 = element.getProperty("P4").toString()
                    items.add(Question(content = content, answerA = p1, answerB = p2, answerC = p3, answerD = p4))
                    index++
                }
                val bundle = Bundle()
                bundle.putString("info", Gson().toJson(question))
                bundle.putString("question", Gson().toJson(items))
                bundle.putString("title", question.subjectTitle)
                navigator.switchActivity<HomeActivity>(bundle)
                listener.hideProgressDialog()
            } catch (ex: Exception) {
                navigator.toast("Không tải được câu hỏi, hãy thử lại.")
                listener.hideProgressDialog()
            }
        }
    }
}