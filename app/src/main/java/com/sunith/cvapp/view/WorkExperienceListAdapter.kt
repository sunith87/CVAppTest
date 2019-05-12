package com.sunith.cvapp.view

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sunith.cvapp.R
import com.sunith.cvapp.data.models.domain.Domain

class WorkExperienceListAdapter(private val workExperiences: List<Domain.WorkExperienceData>) :
    RecyclerView.Adapter<WorkExperienceViewHolder>() {
    override fun onCreateViewHolder(vg: ViewGroup, position: Int): WorkExperienceViewHolder {
        val inflater = LayoutInflater.from(vg.context)
        val itemView = inflater.inflate(R.layout.work_experience_itemview, vg, false)
        return WorkExperienceViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return workExperiences.size
    }

    override fun onBindViewHolder(vh: WorkExperienceViewHolder, position: Int) {
        vh.bind(workExperiences.get(position))
    }
}

class WorkExperienceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(workExperience: Domain.WorkExperienceData?) {
        workExperience?.let {
            val companyName = itemView.findViewById<TextView>(R.id.work_experience_company_name)
            companyName.text = it.companyName
            val companyTimeLine = itemView.findViewById<TextView>(R.id.work_experience_timeline)
            companyTimeLine.text = it.companyTimeLine
            val allProjects = it.projectDataList
            allProjects?.let {
                val projectList = itemView.findViewById<RecyclerView>(R.id.projectList)
                projectList.adapter = ProjectListAdapter(it)
                projectList.layoutManager = LinearLayoutManager(itemView.context)
                projectList.setHasFixedSize(true)
            }
        }
    }
}
