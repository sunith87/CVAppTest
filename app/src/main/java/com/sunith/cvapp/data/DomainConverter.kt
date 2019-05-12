package com.sunith.cvapp.data

import com.sunith.cvapp.data.models.domain.Domain
import com.sunith.cvapp.data.models.raw.Raw

class DomainConverter {

    fun convert(rawCvs: List<Raw.CV>): List<Domain.CVData> {
        return rawCvs.map { convertRawToDomain(it) }
    }

    private fun convertRawToDomain(rawCv: Raw.CV): Domain.CVData {
        val professionalSummary = rawCv.professional_summary
        val skills = rawCv.skills
        val profileData = Domain.ProfileData(rawCv.profile.name, addressString(rawCv.profile))
        val workExperienceData = mutableListOf<Domain.WorkExperienceData>()
        rawCv.work_experience?.forEach { rawWorkExperience ->
            val projectData = rawWorkExperience.projects.map { project ->
                Domain.ProjectData(getNameAndRole(project), project.summary, project.highlights)
            }
            workExperienceData.add(
                Domain.WorkExperienceData(rawWorkExperience.company, getTimeLineText(rawWorkExperience), projectData)
            )
        }

        val educationData = rawCv.education?.map {
            Domain.EducationData(getEducationHeader(it), it.comments)
        }

        return Domain.CVData(
            educationData,
            workExperienceData,
            profileData,
            skills,
            professionalSummary
        )
    }

    private fun getNameAndRole(project: Raw.Project): String {
        val sb = StringBuilder()
        sb.append(project.name)
        sb.append(", ")
        sb.append(project.role)
        return sb.toString()
    }

    private fun getTimeLineText(workExperience: Raw.WorkExperience): String {
        val sb = StringBuilder()
        sb.append(workExperience.start_date)
        sb.append(" - ")
        sb.append(workExperience.end_date)
        return sb.toString()
    }

    private fun getEducationHeader(education: Raw.Education): String {
        val sb = StringBuilder()
        sb.append(education.name)
        sb.append("\n")
        sb.append(education.qualification)
        sb.append("\n")
        sb.append(education.start)
        sb.append(" - ")
        sb.append(education.end)
        return sb.toString()
    }

    private fun addressString(profile: Raw.Profile): String {
        val sb = StringBuilder()
        val address = profile.address

        if (address.line1.isNotEmpty()) {
            sb.append(address.line1)
            sb.append(", ")
        }
        if (address.line2.isNotEmpty()) {
            sb.append(address.line2)
            sb.append(", ")
        }
        if (address.town.isNotEmpty()) {
            sb.append(address.town)
            sb.append(", ")
        }
        if (address.postcode.isNotEmpty()) {
            sb.append(address.postcode)
            sb.append(", ")
        }
        if (profile.email.isNotEmpty()) {
            sb.append(profile.email)
            sb.append(", ")
        }
        if (profile.phone.isNotEmpty()) {
            sb.append(profile.phone)
            sb.append("(P)")
            sb.append(", ")
        }
        if (profile.mobile.isNotEmpty()) {
            sb.append(profile.mobile)
            sb.append("(M)")
        }

        return sb.toString()
    }
}