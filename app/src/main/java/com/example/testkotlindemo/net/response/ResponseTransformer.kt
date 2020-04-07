package com.example.testkotlindemo.net.response

import com.example.testkotlindemo.net.Constant
import com.example.testkotlindemo.net.exception.ApiException
import com.example.testkotlindemo.net.exception.CustomException
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.functions.Function

class ResponseTransformer {

    fun <T> handleResult(): ObservableTransformer<Response<T>, T> {
        return ObservableTransformer { upstream ->
            upstream.onErrorResumeNext(ErrorResumeFunction())
                .flatMap(ResponseFunction())
        }
    }

    internal inner class ErrorResumeFunction<T> : Function<Throwable, ObservableSource<out Response<T>>> {

        override fun apply(throwable: Throwable): ObservableSource<out Response<T>> {
            return Observable.error(CustomException.handleException(throwable))
        }
    }

    internal inner class ResponseFunction<T> : Function<Response<T>, ObservableSource<T>> {

        override fun apply(tResponse: Response<T>): ObservableSource<T> {
            val code = tResponse.status
            val message = tResponse.message
            return if (code == Constant.REQUEST_SUCCESS_CODE) {
                Observable.just(tResponse.data)
            } else {
                Observable.error(ApiException(code!!.toInt(), message.toString()))
            }
        }
    }
}