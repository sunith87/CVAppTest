package com.sunith.cvapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.sunith.cvapp.data.CVDataProviderModule
import com.sunith.cvapp.data.models.domain.Domain
import com.sunith.cvapp.presenter.CVContract
import com.sunith.cvapp.presenter.CVPresenter
import com.sunith.cvapp.presenter.RxModuleImpl
import com.sunith.cvapp.view.CvsAdapter

class CVActivity : AppCompatActivity(), CVContract.View {

    private lateinit var cvsAdapter: CvsAdapter
    private lateinit var presenter: CVContract.Presenter
    private lateinit var cvsView: RecyclerView
    private lateinit var fetchingProgressView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cv_main)
        cvsView = findViewById(R.id.main_cvs_view);
        fetchingProgressView = findViewById(R.id.fetchingDataView);
        presenter = CVPresenter(CVDataProviderModule.getCVDataProvider(this), RxModuleImpl)
        cvsAdapter = CvsAdapter(this)
        cvsView.adapter = cvsAdapter
        cvsView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume(this)
        fetchData()
    }

    override fun onPause() {
        presenter.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun renderCVs(allCvs: List<Domain.CVData>) {
        cvsAdapter.setCVs(allCvs)
    }

    override fun showProgressView() {
        fetchingProgressView.visibility = View.VISIBLE
    }

    override fun hideProgressView() {
        fetchingProgressView.visibility = View.INVISIBLE
    }

    override fun error(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.refetch) { _ -> fetchData() }
            .show();
    }

    private fun fetchData() {
        presenter.fetchData()
    }


}