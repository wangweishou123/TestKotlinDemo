package com.example.testkotlindemo.net.response

import android.support.annotation.Keep

@Keep
class Response <T>{
    var status:Int?=null
    var data:T?=null
    var message:String?=null

    override fun toString(): String {
        return "Response{" +
                "status=" + status +
                ", data=" + data +
                ", message='" + message + '\''.toString() +
                '}'.toString()
    }
}