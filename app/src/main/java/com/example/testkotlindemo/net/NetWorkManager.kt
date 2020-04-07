package com.example.testkotlindemo.net

import android.content.Intent
import android.support.annotation.NonNull
import android.text.TextUtils
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.Utils
import com.example.testkotlindemo.BuildConfig
import com.example.testkotlindemo.net.request.Request
import com.example.testkotlindemo.test.MainActivity
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class NetWorkManager {

    companion object {
        private val DEFAULT_TIMEOUT = 60

        private var retrofit: Retrofit? = null
        @Volatile
        private var request: Request? = null

        val instance: NetWorkManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetWorkManager() }

        fun getRequest(): Request {
            if (request == null) {
                synchronized(Request::class.java) {
                    request = retrofit!!.create(Request::class.java)
                }
            }
            return this.request!!
        }

        fun init() {
            val dispatcher = Dispatcher()
            dispatcher.maxRequests = 1

            val httpClient = OkHttpClient.Builder()
            httpClient.connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .authenticator(TokenAuthenticator())
                .dispatcher(dispatcher)
            httpClient.addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(@NonNull chain: Interceptor.Chain): Response {
                    val headers = chain.request().headers
                    val isAddLanguage = headers["isAddLanguage"] != null && "0" == headers["isAddLanguage"]

                    val path = chain.request().url.encodedPath
                    val builder = chain.request().url.newBuilder()
                    if (!isAddLanguage) {
                        val newPath = "/" + Constant.LANGUAGE + path
                        builder.encodedPath(newPath)
                    }

                    val request = chain.request().newBuilder().url(builder.build())
                        .addHeader("NationID", BuildConfig.NationID)
                        .addHeader("Accept", BuildConfig.Accept)
                        .addHeader("Accept-Language", Constant.LANGUAGE)
                        .addHeader(
                            "Authorization", if (TextUtils.isEmpty(Constant.USER_TOKEN))
                                ""
                            else
                                Constant.TOKEN_TYPE.plus(" ").plus(Constant.USER_TOKEN)
                        )
                        .build()

                    return chain.proceed(request)
                }
            })
            httpClient.addNetworkInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(@NonNull chain: Interceptor.Chain): Response {
                    val proceed = chain.proceed(chain.request())
                    val code = proceed.code

                    if (code == 406) {
                        val intent = Intent(Utils.getApp(), MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        ActivityUtils.startActivity(intent)

                        return Response.Builder()
                            .code(200)
                            .body(
                                ResponseBody.create(
                                    "application/json; charset=utf-8".toMediaTypeOrNull(),
                                    "{\"status\":100,\"data\":null,\"message\":\"System Maintenance\"}"
                                )
                            )
                            .protocol(Protocol.HTTP_2)
                            .message("System Maintenance")
                            .request(chain.request())
                            .build()
                    } else {
                        return proceed
                    }
                }
            })

            if (BuildConfig.DEBUG) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                httpClient.addInterceptor(httpLoggingInterceptor)
            }

            val client = httpClient.build()
            retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(Constant.HOST)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}