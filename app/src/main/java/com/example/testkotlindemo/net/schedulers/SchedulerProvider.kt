package com.example.testkotlindemo.net.schedulers

import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulerProvider :BaseSchedulerProvider{

    companion object {
        val instance: SchedulerProvider by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SchedulerProvider() }
    }


    override fun computation(): Scheduler {
        return Schedulers.computation()
    }

    override fun io(): Scheduler {
        return Schedulers.io()
    }

    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    override fun <T> applySchedulers(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable.subscribeOn(this@SchedulerProvider.io())
                .observeOn(this@SchedulerProvider.ui())
        }
    }
}