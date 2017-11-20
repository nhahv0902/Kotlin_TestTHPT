package com.example.nhahv.testthpt.home

import android.databinding.Bindable
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.os.StrictMode
import android.view.View
import com.example.nhahv.testthpt.BR
import com.example.nhahv.testthpt.BaseRecyclerAdapter
import com.example.nhahv.testthpt.BaseViewModel
import com.example.nhahv.testthpt.R
import com.example.nhahv.testthpt.data.AnswerQuestion
import com.example.nhahv.testthpt.data.Question
import com.example.nhahv.testthpt.util.Constant.METHOD_INFO_TEST
import com.example.nhahv.testthpt.util.Constant.METHOD_POST_ANSWER
import com.example.nhahv.testthpt.util.Constant.METHOD_UPDATE_MATSDT
import com.example.nhahv.testthpt.util.Constant.NAME_SPACE
import com.example.nhahv.testthpt.util.Constant.SOAP_INFO_TEST
import com.example.nhahv.testthpt.util.Constant.SOAP_POST_ANSWER
import com.example.nhahv.testthpt.util.Constant.SOAP_UPDATE_MATSDT
import com.example.nhahv.testthpt.util.Constant.URL
import com.example.nhahv.testthpt.util.Constant.URL_INFO_TEST
import com.example.nhahv.testthpt.util.Navigator
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapPrimitive
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE

/**
 * Created by nhahv on 11/7/17.
 */
class HomeViewModel(private val navigator: Navigator, private val listener: HomeListener, private val maDT: Long = -1, private val maTSDT: Long = -1) : BaseViewModel(), BaseRecyclerAdapter.OnClickItem<Question> {
    val loading = ObservableInt(View.VISIBLE)
    var items = ArrayList<Question>()
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.items)
        }

    var contentQuestion: String? = null
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.contentQuestion)
        }

    var pA: String? = null
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.pA)
        }
    var pB: String? = null
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.pB)
        }

    var pC: String? = null
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.pC)
        }

    var pD: String? = null
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.pD)
        }
    var textStart: String? = navigator.string(R.string.text_start_test)
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.textStart)
        }

    var next: Boolean = false
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.pD)
        }

    var previous: Boolean = false
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.pD)
        }


    var answer: StringBuilder = StringBuilder()
    val running = ObservableBoolean(false)

    val numberQuestion = ObservableInt(0)
    val adapter = ObservableField<BaseRecyclerAdapter<Question>>(BaseRecyclerAdapter(items, R.layout.nav_drawer_row, this))
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
            request.addProperty("maDeThi", 140)
            val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
            envelope.dotNet = true
            envelope.setOutputSoapObject(request)

            val http = HttpTransportSE(URL_INFO_TEST)
            http.call(SOAP_INFO_TEST, envelope)

            val response = envelope.response as SoapObject
            loading.set(View.GONE)

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

            numberQuestion.set(items.count())

            var i = 0
            while (i < numberQuestion.get()) {
                answer.append('0')
                i++
            }
        }
    }

    override fun onClickItem(item: Question, position: Int) {
        if (running.get()) {
            currentQuestion.set(position)
            updateDataUI(position)
        }
        listener.closeDrawer()
    }

    fun updateDataUI(position: Int) {
        contentQuestion = items[position].content
        pA = items[position].answerA
        pB = items[position].answerB
        pC = items[position].answerC
        pD = items[position].answerD
    }

    fun checkQuestionMade() {
        numberMade.set(items.count { it.answer != AnswerQuestion.NOT })
        updateDataUI(currentQuestion.get())
        listener.setAnswer(items[currentQuestion.get()].answer)
    }


    fun startTest() {
        doAsync {
            val request = SoapObject(NAME_SPACE, METHOD_UPDATE_MATSDT)
            request.addProperty("maTSDT", maTSDT)
            request.addProperty("tinhtrang", 2)
            val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
            envelope.dotNet = true
            envelope.setOutputSoapObject(request)

            val http = HttpTransportSE(URL)
            http.call(SOAP_UPDATE_MATSDT, envelope)

            val response = envelope.response as SoapPrimitive
            if (response.toString().toBoolean()) {
                updateDataUI(0)

                textStart = navigator.string(R.string.text_end_test)
                running.set(!running.get())
            }
        }
    }

    fun endTest() {
        navigator.showDialogConfirm({
            postAnswer()
        })
    }


    fun postAnswer() {
        doAsync {
            val request = SoapObject(NAME_SPACE, METHOD_POST_ANSWER)
            request.addProperty("BaiLam", answer.toString())
            request.addProperty("maDeThi", maDT)
            val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
            envelope.dotNet = true
            envelope.setOutputSoapObject(request)

            val http = HttpTransportSE(URL)
            http.call(SOAP_POST_ANSWER, envelope)

            val response = envelope.response as SoapPrimitive
            navigator.log("${response.toString().toFloat()}")
            updateTest()
            uiThread {
                navigator.showDialogCount({ navigator.finish() }, response.toString().toFloat().toString())
                running.set(!running.get())
            }
        }
    }

    fun updateTest() {
        doAsync {
            val request = SoapObject(NAME_SPACE, METHOD_UPDATE_MATSDT)
            request.addProperty("maTSDT", maTSDT)
            request.addProperty("tinhtrang", 3)
            val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
            envelope.dotNet = true
            envelope.setOutputSoapObject(request)

            val http = HttpTransportSE(URL)
            http.call(SOAP_UPDATE_MATSDT, envelope)

            val response = envelope.response as SoapPrimitive // boolean
        }
    }

    fun updateAnswer(ans: AnswerQuestion) {
        items[currentQuestion.get()].answer = ans
        answer[currentQuestion.get()] = ans.getValueChar()
        navigator.log("$answer")
        adapter.notifyChange()
    }
}