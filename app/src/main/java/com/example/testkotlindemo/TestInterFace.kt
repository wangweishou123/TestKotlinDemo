package com.example.testkotlindemo

import android.view.View
import android.widget.TextView

class TestInterFace {
    var tvOnClickListener:TvOnClickListener?=null


    interface TvOnClickListener{
        fun textChanged(text:String)
    }
    fun getTvOnClickListener(listener: TvOnClickListener){
        tvOnClickListener=listener
    }
    fun initData(view: TextView){
        view.setOnClickListener(){
            tvOnClickListener!!.textChanged(view.text.toString())
        }
    }
}