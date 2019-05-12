package com.sunith.cvapp.presenter

import com.sunith.cvapp.data.models.domain.Domain

interface CVContract {

    interface Presenter {
        fun onResume(view: View)
        fun fetchData()
        fun onPause()
        fun onDestroy()
    }

    interface View {
        fun renderCVs(allCvs: List<Domain.CVData>)
        fun error(message: String)
        fun showProgressView()
        fun hideProgressView()
    }
}