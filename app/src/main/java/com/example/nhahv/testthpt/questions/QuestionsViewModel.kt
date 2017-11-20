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
import com.example.nhahv.testthpt.home.HomeActivity
import com.example.nhahv.testthpt.util.Constant.METHOD_GET_QUESTION
import com.example.nhahv.testthpt.util.Constant.NAME_SPACE
import com.example.nhahv.testthpt.util.Constant.SOAP_GET_QUESTION
import com.example.nhahv.testthpt.util.Constant.URL
import com.example.nhahv.testthpt.util.Navigator
import org.jetbrains.anko.doAsync
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE

/**
 * Created by nhahv on 09/11/2017.
 */
class QuestionsViewModel(private val navigator: Navigator) : BaseViewModel(), BaseRecyclerAdapter.OnClickItem<InfoQuestion> {

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
            request.addProperty("TenDangNhap", "an")
            val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
            envelope.dotNet = true
            envelope.setOutputSoapObject(request)

            val http = HttpTransportSE(URL)
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
                items.add(InfoQuestion(maDT = maDT.toLong(), subjectTitle = tenMH, nameTest = tenBaiThi, maTSDT = maTSDT.toLong()))
                index++
            }
            adapter.notifyChange()
        }
    }

    override fun onClickItem(item: InfoQuestion, position: Int) {
        val bundle = Bundle()
        bundle.putLong("maDT", item.maDT)
        bundle.putString("tenMH", item.subjectTitle)
        bundle.putLong("maTSDT", item.maTSDT)
        navigator.switchActivity<HomeActivity>(bundle)
    }


}