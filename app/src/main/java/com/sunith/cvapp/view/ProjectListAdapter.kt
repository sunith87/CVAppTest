package com.sunith.cvapp.view

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sunith.cvapp.R
import com.sunith.cvapp.data.models.domain.Domain

class ProjectListAdapter(private val allProjects: List<Domain.ProjectData>) : RecyclerView.Adapter<ProjectViewHolder>() {
    override fun onBindViewHolder(vh: ProjectViewHolder, position: Int) {
        vh.bind(allProjects.get(position))
    }

    override fun onCreateViewHolder(vg: ViewGroup, p1: Int): ProjectViewHolder {
        val inflater = LayoutInflater.from(vg.context)
        val itemView = inflater.inflate(R.layout.project_layout, vg, false)
        return ProjectViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return allProjects.size;
    }
}

class ProjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(project: Domain.ProjectData) {
        val projectNameRole = itemView.findViewById<TextView>(R.id.project_name_role)
        val projectSummary = itemView.findViewById<TextView>(R.id.project_summary)
        val projectHighlights = itemView.findViewById<RecyclerView>(R.id.project_highlights)
        projectNameRole.text = project.nameAndRole
        projectSummary.text = project.summary
        projectHighlights.layoutManager = LinearLayoutManager(itemView.context)
        projectHighlights.adapter = SkillsListAdapter(itemView.context, project.highlights)
    }


}
