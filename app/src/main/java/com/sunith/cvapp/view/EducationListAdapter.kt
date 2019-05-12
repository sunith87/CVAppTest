package com.sunith.cvapp.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sunith.cvapp.R
import com.sunith.cvapp.data.models.domain.Domain

class EducationListAdapter(private val educationList: List<Domain.EducationData>) :
    RecyclerView.Adapter<EducationViewHolder>() {
    override fun getItemCount(): Int {
        return educationList.size
    }

    override fun onBindViewHolder(vh: EducationViewHolder, position: Int) {
        vh.bind(educationList.get(position))
    }

    override fun onCreateViewHolder(vg: ViewGroup, p1: Int): EducationViewHolder {
        val inflater = LayoutInflater.from(vg.context)
        val itemView = inflater.inflate(R.layout.education_itemview, vg, false)
        return EducationViewHolder(itemView)
    }
}

class EducationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(education: Domain.EducationData) {
        val educationHeader = itemView.findViewById<TextView>(R.id.education_header)
        val educationComments = itemView.findViewById<TextView>(R.id.education_comments)
        educationHeader.text = education.header
        educationComments.text = education.comments
    }
}
