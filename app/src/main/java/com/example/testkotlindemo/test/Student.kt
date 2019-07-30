package com.example.testkotlindemo.test

import java.io.Serializable

class Student :Serializable{
    constructor()
    constructor(n:String,g:String){
        name=n
        gender=g
    }
    var name:String?=null;
    var studentId:Int?=null
    var age:Int?=null
    var gender:String?=null
}