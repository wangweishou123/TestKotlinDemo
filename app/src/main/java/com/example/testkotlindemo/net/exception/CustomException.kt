package com.example.testkotlindemo.net.exception

import android.net.ParseException
import android.text.TextUtils
import com.google.gson.JsonParseException
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class CustomException {

    companion object {
        /**
         * 未知错误
         */
        val UNKNOWN = 1000

        /**
         * 解析错误
         */
        val PARSE_ERROR = 1001

        /**
         * 网络错误
         */
        val NETWORK_ERROR = 1002

        /**
         * 协议错误
         */
        val HTTP_ERROR = 1003
        fun handleException(e:Throwable):ApiException{
            var exceptionMessage=""
            if (TextUtils.isEmpty(e.message)){
                exceptionMessage = e.message.toString()
            }
            var ex:ApiException
            if (e is JsonParseException || e is JSONException || e is ParseException) {
                //解析错误
                ex = ApiException(PARSE_ERROR, exceptionMessage)
                return ex
            } else if (e is ConnectException) {
                //网络错误
                ex = ApiException(NETWORK_ERROR, exceptionMessage)
                return ex
            } else if (e is UnknownHostException || e is SocketTimeoutException) {
                //连接错误
                ex = ApiException(NETWORK_ERROR, exceptionMessage)
                return ex
            } else {
                //未知错误
                ex = ApiException(UNKNOWN, exceptionMessage)
                return ex
            }
        }
    }


}