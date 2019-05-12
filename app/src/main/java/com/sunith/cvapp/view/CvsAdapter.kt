package com.sunith.cvapp.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sunith.cvapp.R
import com.sunith.cvapp.data.models.domain.Domain

class CvsAdapter(private val context: Context) : RecyclerView.Adapter<CvsViewHolder>() {

    private var allCVs: List<Domain.CVData>? = null

    fun setCVs(allCvs: List<Domain.CVData>) {
        this.allCVs = allCvs
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return allCVs?.size ?: 0
    }

    override fun onBindViewHolder(vh: CvsViewHolder, position: Int) {
        allCVs?.let {
            vh.bindData(it.get(position))
        }
    }

    override fun onCreateViewHolder(vg: ViewGroup, p1: Int): CvsViewHolder {
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.inidividual_cv, vg, false)
        return CvsViewHolder(itemView)
    }
}