package com.example.testkotlindemo.net.request

import com.example.testkotlindemo.mode.LoginBean
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*

interface Request {
    /**
     * 下载文件
     */
    @Streaming
    @GET
    abstract fun downloadFile(@Url fileUrl: String): Observable<ResponseBody>

    /**
     * 登录/刷新
     *
     * @param username  用户名
     * @param password  密码
     * @param grantType 固定字段 登录：password  /  刷新：refresh_token
     */
    @Headers("isAddLanguage:0")
    @FormUrlEncoded
    @POST("Auth/login")
    abstract fun login(
        @Field("username") username: String?,
        @Field("password") password: String?,
        @Field("grant_type") grantType: String,
        @Field("refresh_token") refreshToken: String
    ): Observable<LoginBean>

}