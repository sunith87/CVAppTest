package com.sunith.cvapp.presenter

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

object RxModuleImpl : RxModule {

    override fun ioScheduler(): Scheduler {
        return Schedulers.io()
    }

    override fun mainScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    override fun compositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }
}

interface RxModule {
    fun ioScheduler(): Scheduler
    fun mainScheduler(): Scheduler
    fun compositeDisposable(): CompositeDisposable
}


