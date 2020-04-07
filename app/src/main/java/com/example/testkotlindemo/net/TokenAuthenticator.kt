package com.example.testkotlindemo.net

import android.content.Intent
import android.text.TextUtils
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.Utils
import com.example.testkotlindemo.test.MainActivity
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator : Authenticator {

    private val MAX_RETRY_COUNT = 10

    private var retryCount: Int = 0

    override fun authenticate(route: Route?, response: Response): Request? {
        if (TextUtils.isEmpty(Constant.REFRESH_TOKEN)) {
//            GeneralMethodUtils.logoutClearData()
            val intent = Intent(Utils.getApp(), MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            ActivityUtils.startActivity(intent)
            return null
        }
        if (retryCount > MAX_RETRY_COUNT) {
            Constant.REFRESH_TOKEN = ""
        }

        retryCount++

        Constant.USER_TOKEN = ""
        val reLoginObservable = NetWorkManager.getRequest().login(
            null, null, "refresh_token",
            Constant.REFRESH_TOKEN)
        val reLoginBeanResponse = reLoginObservable.blockingFirst()

        if (reLoginBeanResponse == null || reLoginBeanResponse.status !== 0 ||
            TextUtils.isEmpty(reLoginBeanResponse.access_token)) {
//            GeneralMethodUtils.logoutClearData()
            val intent = Intent(Utils.getApp(), MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            ActivityUtils.startActivity(intent)
            return null
        }

//        GeneralMethodUtils.completeLoginSaveInfo(reLoginBeanResponse)
        return response.request.newBuilder()
            .header("Authorization", Constant.TOKEN_TYPE.plus(" ").plus(Constant.USER_TOKEN))
            .build()
    }
}