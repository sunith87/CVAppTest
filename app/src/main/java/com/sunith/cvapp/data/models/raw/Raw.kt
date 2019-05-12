package com.sunith.cvapp.data.models.raw

class Raw {
    data class CV(
        val profile: Profile,
        val professional_summary: String,
        val skills: List<String>?,
        val work_experience: List<WorkExperience>?,
        val education: List<Education>?
    )

    data class WorkExperience(
        val company: String,
        val start_date: String,
        val end_date: String,
        val projects: List<Project>
    )

    data class Project(
        val name: String,
        val role: String,
        val summary: String,
        val highlights: List<String>
    )

    data class Profile(
        val name: String,
        val email: String,
        val phone: String,
        val mobile: String,
        val address: Address
    )

    data class Education(
        val name: String,
        val qualification: String,
        val start: String,
        val end: String,
        val comments: String
    )

    data class Address(
        val line1: String,
        val line2: String,
        val town: String,
        val postcode: String
    )
}