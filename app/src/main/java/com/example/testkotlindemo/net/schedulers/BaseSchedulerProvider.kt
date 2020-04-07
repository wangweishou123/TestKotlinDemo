package com.example.testkotlindemo.net.schedulers

import android.support.annotation.NonNull
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler

interface BaseSchedulerProvider {

    @NonNull
    abstract fun computation(): Scheduler

    @NonNull
    abstract fun io(): Scheduler

    @NonNull
    abstract fun ui(): Scheduler

    @NonNull
    abstract fun <T> applySchedulers(): ObservableTransformer<T, T>
}