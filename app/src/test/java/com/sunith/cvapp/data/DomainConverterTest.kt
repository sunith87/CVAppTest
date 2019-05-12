package com.sunith.cvapp.data

import com.nhaarman.mockitokotlin2.whenever
import com.sunith.cvapp.data.models.raw.Raw
import junit.framework.Assert.assertEquals
import junit.framework.Assert.fail
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.MockitoAnnotations

class DomainConverterTest {

    companion object {
        const val EXPECTED_ADDRESS =
            "120 flat, some street, ThisTown, LK90 2OP, some@email.com, 098908990(P), 0809090909(M)"
        const val EXPECTED_NAME = "Mr Some Guy"
        const val EXPECTED_PROFESSIONAL_SUMMARY = "some summary"
        const val EXPECTED_EDUCATION_HEADER = "Computer Systems\nMEng\n2006 - 2010"
        const val EXPECTED_EDUCATION_COMMENTS = "First class"
        const val EXPECTED_COMPANY_NAME = "XYZ company"
        const val EXPECTED_COMPANY_TIMELINE = "2011 - 2012"
        const val EXPECTED_PROJECT_NAME_ROLE = "mobile video streaming, developer"
        const val EXPECTED_PROJECT_HIGHLIGHTS = "android, mobile, libraray, streams "
        const val EXPECTED_PROJECT_SUMMARY = "development, test, video, streaming"
    }

    private lateinit var domainConverter: DomainConverter

    @Mock
    private lateinit var mockRawCV: Raw.CV

    private var mockCvs = mutableListOf<Raw.CV>()
    private var mockEducationList = mutableListOf<Raw.Education>()
    private var mockWorkExperienceList = mutableListOf<Raw.WorkExperience>()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        domainConverter = DomainConverter()
        mockCvs.add(mockRawCV)
        whenever(mockRawCV.profile).thenReturn(getMockProfile())
    }

    @Test
    fun testConvert_shouldReturnCorrectProfile() {
        val convert = domainConverter.convert(mockCvs)

        assertEquals(EXPECTED_NAME, convert[0].profileData?.name)
        assertEquals(EXPECTED_ADDRESS, convert[0].profileData?.address)
    }

    @Test
    fun testConvert_shouldReturnCorrectProfessionalSummary() {
        whenever(mockRawCV.professional_summary).thenReturn("some summary")

        val convert = domainConverter.convert(mockCvs)

        assertEquals(EXPECTED_PROFESSIONAL_SUMMARY, convert[0].professionalSummary)
    }

    @Test
    fun testConvert_shouldReturnCorrectSkills() {
        val skillsList = arrayOf("jave", "kotlin", "junit", "mockito").toList()
        whenever(mockRawCV.skills).thenReturn(skillsList)

        val convert = domainConverter.convert(mockCvs)

        assertEquals(skillsList, convert[0].skillsList)
    }

    @Test
    fun testConvert_shouldReturnCorrectEducation() {
        mockEducationList.add(getMockEducation())
        whenever(mockRawCV.education).thenReturn(mockEducationList)

        val convert = domainConverter.convert(mockCvs)

        assertEquals(EXPECTED_EDUCATION_HEADER, convert[0].educationDataList!![0].header)
        assertEquals(EXPECTED_EDUCATION_COMMENTS, convert[0].educationDataList!![0].comments)
    }

    @Test
    fun testConvert_shouldReturnCorrectWorkExperience() {
        mockWorkExperienceList.add(getMockWorkExperience())
        whenever(mockRawCV.work_experience).thenReturn(mockWorkExperienceList)

        val convert = domainConverter.convert(mockCvs)

        assertEquals(EXPECTED_COMPANY_NAME, convert[0].workExperienceDataList!![0].companyName)
        assertEquals(EXPECTED_COMPANY_TIMELINE, convert[0].workExperienceDataList!![0].companyTimeLine)
    }

    @Test
    fun testConvert_shouldReturnCorrectProjects() {
        mockWorkExperienceList.add(getMockWorkExperience())
        whenever(mockRawCV.work_experience).thenReturn(mockWorkExperienceList)

        val convert = domainConverter.convert(mockCvs)
        val projectDataList = convert[0].workExperienceDataList!![0].projectDataList

        projectDataList?.let {
            assertEquals(EXPECTED_PROJECT_NAME_ROLE, it[0].nameAndRole)
            assertEquals(EXPECTED_PROJECT_HIGHLIGHTS, it[0].highlights.joinToString { "$it " })
            assertEquals(EXPECTED_PROJECT_SUMMARY, it[0].summary)
        } ?: fail()
    }


    private fun getMockWorkExperience(): Raw.WorkExperience {
        val mutableListOfProjects = mutableListOf<Raw.Project>()
        mutableListOfProjects.add(getProject())
        return Raw.WorkExperience(
            "XYZ company",
            "2011",
            "2012",
            mutableListOfProjects
        )
    }

    private fun getProject(): Raw.Project {
        return Raw.Project(
            "mobile video streaming",
            "developer",
            "development, test, video, streaming",
            arrayOf("android, mobile, libraray, streams").toList()
        )
    }

    private fun getMockProfile(): Raw.Profile {
        return Raw.Profile(
            "Mr Some Guy",
            "some@email.com",
            "098908990",
            "0809090909",
            getMockAddress()
        )
    }

    private fun getMockEducation(): Raw.Education {
        return Raw.Education(
            "Computer Systems",
            "MEng",
            "2006",
            "2010",
            "First class"
        )
    }

    private fun getMockAddress(): Raw.Address {
        return Raw.Address(
            "120 flat",
            "some street",
            "ThisTown",
            "LK90 2OP"
        )
    }
}