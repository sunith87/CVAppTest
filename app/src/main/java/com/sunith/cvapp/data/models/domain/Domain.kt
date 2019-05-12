package com.sunith.cvapp.data.models.domain

class Domain {

    data class CVData(
        val educationDataList: List<EducationData>?,
        val workExperienceDataList: List<WorkExperienceData>?,
        val profileData: ProfileData?,
        val skillsList: List<String>?,
        val professionalSummary: String?
    )

    data class EducationData(
        val header: String,
        val comments: String
    )

    data class WorkExperienceData(
        val companyName: String,
        val companyTimeLine: String,
        val projectDataList: List<ProjectData>?
    )

    data class ProjectData(
        val nameAndRole: String,
        val summary: String,
        val highlights: List<String>
    )

    data class ProfileData(
        val name: String,
        val address: String
    )
}
