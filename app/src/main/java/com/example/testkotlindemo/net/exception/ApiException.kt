package com.example.testkotlindemo.net.exception

import java.lang.RuntimeException

class ApiException :RuntimeException{
    constructor()
    constructor(n:Int,g:String){
        code=n
        displayMessage=g
    }
    var code:Int?=null
    var displayMessage:String?=null

    override fun getLocalizedMessage(): String {
        return displayMessage.toString()
    }

    override val message: String?
        get() = displayMessage.toString()
}