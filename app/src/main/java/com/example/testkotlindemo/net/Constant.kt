package com.example.testkotlindemo.net

import com.example.testkotlindemo.BuildConfig

open class Constant {
    companion object{
        val HOST = BuildConfig.AppUrl + "/"
        val HOST_PHOTO = BuildConfig.AppUrlPicture + "/Home/UploadImage"

        var REQUEST_SUCCESS_CODE:Int=0


        var TOKEN_TYPE:String=""
        var USER_TOKEN:String=""
        var REFRESH_TOKEN:String=""

        /**
         * 语言
         */
        var LANGUAGE = "zh-CN"
        val LANGUAGE_SIMPLIFIED_CHINESE = "zh-CN"
        val LANGUAGE_TRADITIONAL_CHINESE = "zh-TW"
        val LANGUAGE_ENGLISH = "en-US"
    }

}