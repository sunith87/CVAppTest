package com.sunith.cvapp.view

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import com.sunith.cvapp.R
import com.sunith.cvapp.data.models.domain.Domain

class CvsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindData(cvData: Domain.CVData) {
        setupProfile(cvData.profileData)
        setupProfessionalSummary(cvData.professionalSummary)
        setupTechSkills(cvData.skillsList)
        setupWorkExperience(cvData.workExperienceDataList)
        setupEducation(cvData.educationDataList)
    }

    private fun setupEducation(educationList: List<Domain.EducationData>?) {
        val educationWrapper: View = itemView.findViewById(R.id.education_wrapper)
        if (educationList?.isNotEmpty() == true) {
            educationWrapper.visibility = VISIBLE
            val educationListView: RecyclerView = itemView.findViewById(R.id.educationList)
            val educationAdapter = EducationListAdapter(educationList)
            educationListView.adapter = educationAdapter
            educationListView.layoutManager = LinearLayoutManager(itemView.context)
            expandableListener(educationWrapper, educationListView)
        } else {
            educationWrapper.visibility = GONE
        }
    }

    private fun setupWorkExperience(workExperiences: List<Domain.WorkExperienceData>?) {
        val workExperienceWrapper: View = itemView.findViewById(R.id.work_experience_wrapper)
        if (workExperiences?.isNotEmpty() == true) {
            workExperienceWrapper.visibility = VISIBLE
            val workExperienceList: RecyclerView = itemView.findViewById(R.id.workExperienceList)
            workExperienceList.adapter = WorkExperienceListAdapter(workExperiences)
            workExperienceList.layoutManager = LinearLayoutManager(itemView.context)
            expandableListener(workExperienceWrapper, workExperienceList)
        } else {
            workExperienceWrapper.visibility = GONE
        }
    }

    private fun setupTechSkills(skills: List<String>?) {
        val skillsWrapper: View = itemView.findViewById(R.id.skills_wrapper)
        if (skills?.isNotEmpty() == true) {
            skillsWrapper.visibility = VISIBLE
            val skillsList: RecyclerView = itemView.findViewById(R.id.skillsList)
            val dottedListAdapter = SkillsListAdapter(itemView.context, skills)
            skillsList.adapter = dottedListAdapter
            skillsList.layoutManager = LinearLayoutManager(itemView.context)
            expandableListener(skillsWrapper, skillsList)
        } else {
            skillsWrapper.visibility = GONE
        }
    }

    private fun setupProfessionalSummary(professionalSummary: String?) {
        val professionalSummaryWrapper: View = itemView.findViewById(R.id.professional_summary_wrapper)

        if (professionalSummary?.isNotEmpty() == true) {
            professionalSummaryWrapper.visibility = VISIBLE
            val professionalSummaryView: TextView = itemView.findViewById(R.id.professional_summary)
            professionalSummaryView.text = professionalSummary
        } else {
            professionalSummaryWrapper.visibility = GONE
        }
    }

    private fun setupProfile(profile: Domain.ProfileData?) {
        val profileWrapper: View = itemView.findViewById(R.id.skills_wrapper)
        if (profile != null) {
            profileWrapper.visibility = VISIBLE
            val profileName: TextView = itemView.findViewById(R.id.profile_name)
            profileName.text = profile.name
            val profileAddress: TextView = itemView.findViewById(R.id.profile_address_line)
            profileAddress.text = profile.address
        } else {
            profileWrapper.visibility = GONE
        }
    }

    private fun expandableListener(view: View, list: RecyclerView) {
        view.setOnClickListener { _ ->
            if (list.isShown) {
                list.visibility = GONE
            } else {
                list.visibility = VISIBLE
            }
        }
    }
}
