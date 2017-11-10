package com.example.nhahv.testthpt.login

import android.databinding.ObservableBoolean
import android.os.StrictMode
import com.example.nhahv.testthpt.BaseViewModel
import com.example.nhahv.testthpt.data.local.SharePrefs
import com.example.nhahv.testthpt.home.HomeActivity
import com.example.nhahv.testthpt.util.Constant.METHOD_LOGIN
import com.example.nhahv.testthpt.util.Constant.NAME_SPACE
import com.example.nhahv.testthpt.util.Constant.PASSWORD
import com.example.nhahv.testthpt.util.Constant.SOAP_LOGIN
import com.example.nhahv.testthpt.util.Constant.URL_LOGIN
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

class LoginViewModel(private val navigator: Navigator) : BaseViewModel() {

    var loading = ObservableBoolean(false)

    fun login(userName: String?, password: String?) {
        if (userName.isNullOrEmpty() || password.isNullOrEmpty()) {
            navigator.toast("Ten dang nhap hoac mat khau khong duoc de trong")
            return
        }

        loading.set(true)
        val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        doAsync {
            val request = SoapObject(NAME_SPACE, METHOD_LOGIN)
            request.addProperty(USER_NAME, userName)
            request.addProperty(PASSWORD, password)
            val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
            envelope.dotNet = true
            envelope.setOutputSoapObject(request)

            val http = HttpTransportSE(URL_LOGIN)
            http.call(SOAP_LOGIN, envelope)

            val response: SoapPrimitive = envelope.response as SoapPrimitive
            loading.set(false)
            if (response.toString().toBoolean()) {
                SharePrefs.getInstance(navigator.context).put("user", userName)
                SharePrefs.getInstance(navigator.context).put("password", password)
                SharePrefs.getInstance(navigator.context).put("isLogin", true)

                navigator.switchActivity<HomeActivity>()
                navigator.finish()
            } else {
                uiThread {
                    navigator.toast("Dang nhap khong thanh cong!")
                }
            }
        }
    }

}
