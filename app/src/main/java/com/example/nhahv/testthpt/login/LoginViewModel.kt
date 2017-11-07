package com.example.nhahv.testthpt.login

import android.os.StrictMode
import android.util.Log
import com.example.nhahv.testthpt.BaseViewModel
import com.example.nhahv.testthpt.util.Constant.METHOD_LOGIN
import com.example.nhahv.testthpt.util.Constant.NAME_SPACE
import com.example.nhahv.testthpt.util.Constant.SOAP_LOGIN
import com.example.nhahv.testthpt.util.Constant.URL_LOGIN
import org.jetbrains.anko.doAsync
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapPrimitive
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE

/**
 * Created by nhahv on 11/7/17.
 */

class LoginViewModel : BaseViewModel() {

    fun login(userName: String, password: String) {
        val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        doAsync {
            val request = SoapObject(NAME_SPACE, METHOD_LOGIN)
            request.addProperty("UserName", userName)
            request.addProperty("Password", password)
            val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
            envelope.dotNet = true
            envelope.setOutputSoapObject(request)

            val http = HttpTransportSE(URL_LOGIN)
            http.call(SOAP_LOGIN, envelope)

            val response: SoapPrimitive = envelope.response as SoapPrimitive
            Log.d("TAG", "$response ")

        }
    }


}
