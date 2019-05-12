package com.sunith.cvapp.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sunith.cvapp.R

class SkillsListAdapter(val context: Context, val skills: List<String>) : RecyclerView.Adapter<SkillsViewHolder>() {
    override fun onBindViewHolder(vh: SkillsViewHolder, position: Int) {
        vh.bind(skills.get(position))
    }

    override fun onCreateViewHolder(vg: ViewGroup, p1: Int): SkillsViewHolder {
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.bullet_itemview, vg, false)
        return SkillsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return skills.size
    }
}

class SkillsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(skill: String) {
        val skillTextView = itemView.findViewById<TextView>(R.id.bullet_itemtext)
        skillTextView.text = skill
    }
}
