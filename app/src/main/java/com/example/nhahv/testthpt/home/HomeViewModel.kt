package com.example.nhahv.testthpt.home

import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.os.StrictMode
import android.view.View
import com.example.nhahv.testthpt.BaseRecyclerAdapter
import com.example.nhahv.testthpt.BaseViewModel
import com.example.nhahv.testthpt.R
import com.example.nhahv.testthpt.data.AnswerQuestion
import com.example.nhahv.testthpt.data.Question
import com.example.nhahv.testthpt.util.Constant.METHOD_INFO_TEST
import com.example.nhahv.testthpt.util.Constant.NAME_SPACE
import com.example.nhahv.testthpt.util.Constant.SOAP_INFO_TEST
import com.example.nhahv.testthpt.util.Constant.URL_INFO_TEST
import com.example.nhahv.testthpt.util.Navigator
import org.jetbrains.anko.doAsync
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE

/**
 * Created by nhahv on 11/7/17.
 */
class HomeViewModel(private val navigator: Navigator, private val maDT: Long = 0) : BaseViewModel(), BaseRecyclerAdapter.OnClickItem<Question> {
    val loading = ObservableInt(View.VISIBLE)
    val items = ArrayList<Question>()
    val adapter = ObservableField<BaseRecyclerAdapter<Question>>(BaseRecyclerAdapter<Question>(items, R.layout.nav_drawer_row, this))
    val currentQuestion = ObservableInt(0)
    val numberMade = ObservableInt(0)

    init {
        getTestBtExam()
    }

    private fun getTestBtExam() {
        loading.set(View.VISIBLE)
        val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        doAsync {
            val request = SoapObject(NAME_SPACE, METHOD_INFO_TEST)
            request.addProperty("maDeThi", maDT)
            val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
            envelope.dotNet = true
            envelope.setOutputSoapObject(request)

            val http = HttpTransportSE(URL_INFO_TEST)
            http.call(SOAP_INFO_TEST, envelope)

            val response = envelope.response as SoapObject
            response.toString()
            loading.set(View.GONE)
            response.propertyCount

            var index = 0
            items.clear()
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
            adapter.notifyChange()
        }
    }

    override fun onClickItem(item: Question, position: Int) {
        navigator.toast("$position")
    }

    fun checkQuestionMade() {
        numberMade.set(items.count { it.answer != AnswerQuestion.NOT })
    }
}