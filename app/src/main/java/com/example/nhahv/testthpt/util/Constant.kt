package com.example.nhahv.testthpt.util

/**
 * Created by nhahv on 11/7/17.
 */
object Constant {
    const val NAME_SPACE = "http://tempuri.org/"
    const val URL_LOGIN = "http://qlhsthptnghen.somee.com/sUser.asmx?WSDL"

    const val METHOD_LOGIN = "Check_Login"
    const val SOAP_LOGIN = NAME_SPACE + METHOD_LOGIN

    const val METHOD_REGISTER = "insert"
    const val SOAP_REGISTER = NAME_SPACE + METHOD_REGISTER


    const val USER_NAME = "UserName"
    const val USER_ID = "UserID"
    const val REAL_NAME = "RealName"
    const val PASSWORD = "Password"
    const val GRPID = "GrpID"
    const val MAKT = "MaKT"
    const val REMARK = "Remark"


    const val URL_QUESTIONS = "http://qlhsthptnghen.somee.com/sThongTinDeThi.asmx?wsdl"
    const val METHOD_GET_QUESTION = "getThongTinDeThiCuaThiSinh"
    const val SOAP_GET_QUESTION = NAME_SPACE + METHOD_GET_QUESTION


    const val URL_INFO_TEST = "http://qlhsthptnghen.somee.com/sChiTietCauHoi.asmx?wsdl"
    const val METHOD_INFO_TEST = "getDeThiTheoMaDeThi"
    const val SOAP_INFO_TEST = NAME_SPACE + METHOD_INFO_TEST
}