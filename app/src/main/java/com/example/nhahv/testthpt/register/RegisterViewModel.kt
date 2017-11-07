package com.example.nhahv.testthpt.register

import android.databinding.ObservableBoolean
import com.example.nhahv.testthpt.BaseViewModel
import com.example.nhahv.testthpt.home.HomeActivity
import com.example.nhahv.testthpt.util.Constant
import com.example.nhahv.testthpt.util.Constant.GRPID
import com.example.nhahv.testthpt.util.Constant.MAKT
import com.example.nhahv.testthpt.util.Constant.METHOD_REGISTER
import com.example.nhahv.testthpt.util.Constant.NAME_SPACE
import com.example.nhahv.testthpt.util.Constant.PASSWORD
import com.example.nhahv.testthpt.util.Constant.REAL_NAME
import com.example.nhahv.testthpt.util.Constant.REMARK
import com.example.nhahv.testthpt.util.Constant.SOAP_LOGIN
import com.example.nhahv.testthpt.util.Constant.SOAP_REGISTER
import com.example.nhahv.testthpt.util.Constant.URL_LOGIN
import com.example.nhahv.testthpt.util.Constant.USER_ID
import com.example.nhahv.testthpt.util.Constant.USER_NAME
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
class RegisterViewModel(private val navigator: Navigator) : BaseViewModel() {

    val loading = ObservableBoolean(false)

    fun register(userName: String, password: String, repeatPassword: String) {
        if (userName.isNullOrEmpty() || password.isNullOrEmpty() || repeatPassword.isNullOrEmpty()) {
            navigator.toast("Hay dien day du thong tin")
            return
        }
        if (password != repeatPassword) {
            navigator.toast("Mat khau khong dung")
            return
        }
        doAsync {
            val request = SoapObject(NAME_SPACE, METHOD_REGISTER)

            val newInfo = SoapObject(NAME_SPACE, "info")
            newInfo.addProperty(USER_NAME, userName)
            newInfo.addProperty(PASSWORD, password)
            request.addSoapObject(newInfo)

            val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
            envelope.dotNet = true
            envelope.setOutputSoapObject(request)

            val http = HttpTransportSE(URL_LOGIN)
            http.call(SOAP_REGISTER, envelope)

            val response: SoapPrimitive = envelope.response as SoapPrimitive
            loading.set(false)
            if (response.toString().toBoolean()) {
                navigator.switchActivity<HomeActivity>()
                navigator.finish()

            } else {
                uiThread {
                    navigator.toast("Dang Ky khong thanh cong!")
                }
            }
        }
    }
}