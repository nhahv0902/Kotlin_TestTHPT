package com.example.nhahv.testthpt.home

import android.arch.lifecycle.MutableLiveData
import android.databinding.Bindable
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.os.Bundle
import android.os.StrictMode
import android.text.format.DateUtils
import android.util.MutableBoolean
import android.util.MutableInt
import android.view.View
import com.example.nhahv.testthpt.BR
import com.example.nhahv.testthpt.BaseRecyclerAdapter
import com.example.nhahv.testthpt.BaseViewModel
import com.example.nhahv.testthpt.R
import com.example.nhahv.testthpt.data.AnswerQuestion
import com.example.nhahv.testthpt.data.InfoQuestion
import com.example.nhahv.testthpt.data.Question
import com.example.nhahv.testthpt.point.PointActivity
import com.example.nhahv.testthpt.util.Constant.METHOD_INFO_TEST
import com.example.nhahv.testthpt.util.Constant.METHOD_POST_ANSWER
import com.example.nhahv.testthpt.util.Constant.METHOD_UPDATE_MATSDT
import com.example.nhahv.testthpt.util.Constant.METHOD_UPDATE_STATUS
import com.example.nhahv.testthpt.util.Constant.NAME_SPACE
import com.example.nhahv.testthpt.util.Constant.SOAP_INFO_TEST
import com.example.nhahv.testthpt.util.Constant.SOAP_POST_ANSWER
import com.example.nhahv.testthpt.util.Constant.SOAP_UPDATE_MATSDT
import com.example.nhahv.testthpt.util.Constant.SOAP_UPDATE_STATUS
import com.example.nhahv.testthpt.util.Constant.URL
import com.example.nhahv.testthpt.util.Navigator
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapPrimitive
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE
import java.util.*
import kotlin.concurrent.timerTask

/**
 * Created by nhahv on 11/7/17.
 */
class HomeViewModel(private val navigator: Navigator,
                    private val listener: HomeListener,
                    val questions: ArrayList<Question>,
                    val question: InfoQuestion = InfoQuestion()
) : BaseViewModel(), BaseRecyclerAdapter.OnClickItem<Question> {

    val loading = ObservableInt(View.VISIBLE)

    var contentQuestion: String? = null
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.contentQuestion)
        }

    val pA: ObservableField<String> = ObservableField()
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

    val numberQuestion = questions.size
    val adapter = ObservableField<BaseRecyclerAdapter<Question>>(BaseRecyclerAdapter(questions, R.layout.nav_drawer_row, this))
    val currentQuestion = ObservableInt(0)
    val numberMade = ObservableInt(0)


    var timeCount: Int = 0 // count time
    var timeCountString: String = "" // count time string show UI
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.timeCountString)
        }


    val viewPost = ObservableInt(View.GONE)

    init {
        timeCount = question.time * 60
        timeCountString = DateUtils.formatElapsedTime(timeCount.toLong())

        var i = 0
        while (i < numberQuestion) {
            answer.append('0')
            i++
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
        contentQuestion = questions[position].content
        pA.set(questions[position].answerA)
        pB = questions[position].answerB
        pC = questions[position].answerC
        pD = questions[position].answerD
    }

    fun checkQuestionMade() {
        numberMade.set(questions.count { it.answer != AnswerQuestion.NOT })
        updateDataUI(currentQuestion.get())
        listener.setAnswer(questions[currentQuestion.get()].answer)
    }


    fun startTest() {
        doAsync {
            val request = SoapObject(NAME_SPACE, METHOD_UPDATE_MATSDT)
            request.addProperty("maTSDT", question.maTSDT)
            request.addProperty("tinhtrang", 2)
            val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
            envelope.dotNet = true
            envelope.setOutputSoapObject(request)

            val http = HttpTransportSE(URL)

            try {
                http.call(SOAP_UPDATE_MATSDT, envelope)
                val response = envelope.response as SoapPrimitive
                if (response.toString().toBoolean()) {
                    if (questions.size <= 0) return@doAsync
                    updateDataUI(0)

                    textStart = navigator.string(R.string.text_end_test)
                    running.set(true)

                    val timer = Timer()
                    timer.schedule(timerTask {
                        if (running.get() && timeCount > 0) {
                            timeCount--
                            timeCountString = DateUtils.formatElapsedTime(timeCount.toLong())
                        } else if (timeCount == 0 && running.get()) {
                            postAnswer()
                            running.set(!running.get())
                            timer.cancel()
                            timer.purge()
                        }
                    }, 0, 1000)
                    updateStatus()
                }
            } catch (ex: Exception) {
                navigator.toast("Bắt đầu bài thi chưa thành công, hãy thử lại.")
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
            viewPost.set(View.VISIBLE)
            val request = SoapObject(NAME_SPACE, METHOD_POST_ANSWER)
            request.addProperty("BaiLam", answer.toString())
            request.addProperty("maDeThi", question.maDT)
            val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
            envelope.dotNet = true
            envelope.setOutputSoapObject(request)

            try {
                val http = HttpTransportSE(URL)
                http.call(SOAP_POST_ANSWER, envelope)

                val response = envelope.response as SoapPrimitive

                updateTest()
                running.set(false)

                val bundle = Bundle()
                bundle.putString("info", Gson().toJson(question))
                bundle.putFloat("point", response.toString().toFloat())
                bundle.putString("questions", Gson().toJson(questions))
                viewPost.set(View.GONE)

                navigator.switchActivity<PointActivity>(bundle)
                navigator.finish()
            } catch (ex: Exception) {
                navigator.toast("Nộp bài chưa thành công, hãy thử lại sau.")
                viewPost.set(View.GONE)
            }

        }
    }

    fun updateAnswer() {
        doAsync {
            val request = SoapObject(NAME_SPACE, METHOD_POST_ANSWER)
            request.addProperty("BaiLam", answer.toString())
            request.addProperty("maDeThi", question.maDT)
            val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
            envelope.dotNet = true
            envelope.setOutputSoapObject(request)

            val http = HttpTransportSE(URL)
            http.call(SOAP_POST_ANSWER, envelope)

            val response = envelope.response as SoapPrimitive
        }
    }

    fun updateTest() {
        doAsync {
            val request = SoapObject(NAME_SPACE, METHOD_UPDATE_MATSDT)
            request.addProperty("maTSDT", question.maTSDT)
            request.addProperty("tinhtrang", 3)
            val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
            envelope.dotNet = true
            envelope.setOutputSoapObject(request)

            val http = HttpTransportSE(URL)
            http.call(SOAP_UPDATE_MATSDT, envelope)

            val response = envelope.response as SoapPrimitive // boolean
        }
    }

    fun updateStatus() {
        doAsync {
            val request = SoapObject(NAME_SPACE, METHOD_UPDATE_STATUS)
            request.addProperty("maTSDT", question.maTSDT)
            request.addProperty("tinhTrang", false)
            val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
            envelope.dotNet = true
            envelope.setOutputSoapObject(request)

            val http = HttpTransportSE(URL)
            http.call(SOAP_UPDATE_STATUS, envelope)
        }
    }

    fun updateAnswer(ans: AnswerQuestion) {
        questions[currentQuestion.get()].answer = ans
        answer[currentQuestion.get()] = ans.getValueChar()
        navigator.log("$answer")
        adapter.notifyChange()
    }
}