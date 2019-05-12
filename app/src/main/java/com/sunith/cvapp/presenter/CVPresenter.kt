package com.sunith.cvapp.presenter

import android.util.Log
import com.sunith.cvapp.data.CVDataProvider
import io.reactivex.disposables.CompositeDisposable

class CVPresenter(
    private val cvDataProvider: CVDataProvider,
    private val rxModule: RxModule
) : CVContract.Presenter {

    private var view: CVContract.View? = null
    private val compositeDisposable: CompositeDisposable = rxModule.compositeDisposable()

    override fun onResume(view: CVContract.View) {
        this.view = view
    }

    override fun fetchData() {
        view?.showProgressView()
        compositeDisposable.add(
            cvDataProvider.provideAllCvs()
                .subscribeOn(rxModule.ioScheduler())
                .observeOn(rxModule.mainScheduler())
                .subscribe({
                    view?.hideProgressView()
                    view?.renderCVs(it)
                }, {
                    view?.hideProgressView()
                    view?.error(it.localizedMessage)
                    Log.e("test","issue", it)
                })
        )
    }

    override fun onPause() {
        view = null
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }
}